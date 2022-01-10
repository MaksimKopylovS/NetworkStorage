package client.network;

import client.handler.MessageHandler;
import dto.AuthDTO;
import dto.CountFilesDTO;
import dto.FileUploudDTO;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConnectionToServer {

    private SocketChannel channel;
    private static final Logger LOGGER = Logger.getLogger(ConnectionToServer.class);
    private static final String FILE_CACHE = "FileCache";

    public ConnectionToServer() {
        new Thread(() -> {
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                Bootstrap b = new Bootstrap();
                b.group(workerGroup)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            public void initChannel(SocketChannel socketChannel) throws Exception {
                                channel = socketChannel;
                                socketChannel.pipeline().addLast(new ObjectDecoder(1024 * 1024 * 100, ClassResolvers.cacheDisabled(null)),
                                        new ObjectEncoder(),
                                        new ChannelInboundHandlerAdapter() {

                                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                                Thread.sleep(50);
                                                new MessageHandler(ctx, msg);
                                            }

                                        });
                            }
                        });
                ChannelFuture future = b.connect("localhost", 8189).sync();
                System.out.println("Клиент запущен");
                LOGGER.log(Level.INFO, "Клиент запущен");
                future.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                workerGroup.shutdownGracefully();
            }
        }).start();
    }

    public void sendMessage(Object o) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                channel.writeAndFlush(o);
            }
        });
        t.setDaemon(true);
        t.start();
    }

    public void fileUploud(File file) throws IOException {
        int partCount = 0;
        int sizeOfFile = 1024 * 1024 * 10;
        final byte[] buffer = new byte[sizeOfFile];
        final String fileName = file.getName();

        try(FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis)){
            int bytesAmount = 0;

            if(!Files.exists(Paths.get(FILE_CACHE))){
                Files.createDirectory(Paths.get(FILE_CACHE));
            }

            while ((bytesAmount = bis.read(buffer)) > 0){
                try (FileOutputStream out = new FileOutputStream(FILE_CACHE+"/" + partCount)){
                    out.write(buffer, 0, bytesAmount);
                    Path path = Paths.get(FILE_CACHE+ "/" + partCount);

                    Object o = new FileUploudDTO(path, MessageHandler.getUserName());
                    sendMessage(o);
                    System.out.println("Файл отправлен - " + partCount);
                    partCount++;
                }
            }
            Object o = new CountFilesDTO(partCount, fileName, MessageHandler.getUserName());
            System.out.println("Send partCount");
            sendMessage(o);
            for(File myFile : new File(FILE_CACHE).listFiles()){
                if(myFile.isFile()){
                    myFile.delete();
                }
            }
        }
    }
}
