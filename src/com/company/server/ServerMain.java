package com.company.server;

import com.company.server.Database.DatabaseConnection;

import com.company.server.Programm.ExecutionService;
import com.company.server.Programm.RequestReaderRunnable;

import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.concurrent.atomic.AtomicBoolean;

public class ServerMain {
    // Поскольку ip это localhost, а вот порт меняется, тогда может понадобиться возможность сменить порт
    private static int port = 1334;
    // Колисество потоков в пуле
    private static final int THREAD_COUNT = 5;

    private static ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        // Если предан другой порт в командной строке
        if(args.length == 1){
            try{
                port = Integer.parseInt(args[0]);
            }
            catch (Exception e){
                System.out.println("Что-то не получилось спарсить порт из агрументов командной строки, используется стандартный");
            }
        }

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Отстутствует драйвер базы данных PostgreSQL!");
        }

        ServerSocketChannel serverChannel;
        try{
            serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(port));
            serverChannel.configureBlocking(false);
            System.out.println("Сервер успешно запущен. Порт: " + port);
        }
        catch (IOException exc){
            System.out.println("Ошибка запуска сервера! Скорее всего занят порт!");
            return;
        }


        ExecutionService executionService;
        try{
            DatabaseConnection db = new DatabaseConnection();
            System.out.println("Подключение успешно установлено.");

            executionService = new ExecutionService(db);
        }
        catch (Exception exc){
            System.out.println(exc.getMessage());
            return;
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Выход");
            }
        });

        AtomicBoolean exit = new AtomicBoolean(false);
        Thread thread = getUserInputHandler(exit);
        thread.start();

        while (!exit.get()) {
            try{
                SocketChannel clientChannel = serverChannel.accept();
                if (clientChannel == null){
                    continue;
                }

                RequestReaderRunnable task =  new RequestReaderRunnable(clientChannel, executionService);
                pool.submit(task);
            }
            catch (IOException exc){
                System.out.println(exc.getMessage());
            }
        }
    }

    private static Thread getUserInputHandler(AtomicBoolean exit){
        return new Thread(() -> {
            Scanner scanner = new Scanner(System.in);

            while (true){
                if(scanner.hasNextLine()){
                    String serverCommand = scanner.nextLine();
                    System.out.println("Введено: " + serverCommand);

                    switch (serverCommand){
                        case "exit":
                            exit.set(true);
                            return;
                        default:
                            System.out.println("Такой команды не существует");
                            break;
                    }
                }
                else{
                    exit.set(true);
                    return;
                }
            }
        });
    }
}