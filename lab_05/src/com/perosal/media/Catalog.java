package com.perosal.media;

import com.perosal.Color;
import com.perosal.exceptions.InvalidCommandException;
import com.perosal.exceptions.InvalidPathException;
import com.perosal.media.items.Image;
import com.perosal.media.items.MediaItem;
import com.perosal.media.items.OtherFile;
import com.perosal.media.items.Song;
import com.perosal.shell.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Catalog implements Serializable {
    private static final String FILE_SERIALIZER_NAME = "catalog.ser";
    private final List<MediaItem> mediaItems;

    public Catalog() {
        this.mediaItems = new ArrayList<>();
    }

    public Catalog(List<MediaItem> mediaItems) {
        this.mediaItems = mediaItems;
    }

    public void add(MediaItem item) {
        mediaItems.add(item);
    }

    public List<MediaItem> getMediaItems() {
        return mediaItems;
    }

    public void list() {
        System.out.println(mediaItems);
    }

    public static void play(MediaItem mediaItem) throws IOException {
        Desktop.getDesktop().open(mediaItem.getFile());
    }

    public void save() throws IOException {
        serialize(this);
    }

    public static Catalog load() throws IOException, ClassNotFoundException {
        return (Catalog) deserialize();
    }

    private static void serialize(Object object) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_SERIALIZER_NAME);
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);

        outputStream.writeObject(object);

        outputStream.close();
        fileOutputStream.close();
    }

    private static Object deserialize() throws IOException, ClassNotFoundException {
        Object object;

        FileInputStream fileInputStream = new FileInputStream(FILE_SERIALIZER_NAME);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        object = objectInputStream.readObject();

        return object;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("The catalog contains the following files:\n\n");

        mediaItems.forEach(mediaItem -> {
            List<String> metadataList = mediaItem.getMetadata();

            for (String metadata : metadataList) {
                stringBuilder.append(metadata);
                stringBuilder.append("\n");
            }
            stringBuilder.append("\n");
        });

        return stringBuilder.toString();
    }

    public static MediaItem getMediaItemFromFile(File file) throws InvalidPathException {
        String filePath = file.getPath();
        String extension = filePath.substring(filePath.lastIndexOf("."));

        if (com.perosal.media.items.Image.getSupportedTypes().contains(extension)) {
            return new Image(file);
        }

        if (Song.getSupportedTypes().contains(extension)) {
            return new Song(file);
        }

        return new OtherFile(file);
    }

    public Catalog shell() throws InvalidCommandException, IOException, InvalidPathException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Catalog catalogLoaded = null;
        String commandMessage = "";

        System.out.println("");
        System.out.println(Color.BLUE_BOLD + "This is the catalog shell. Enter exit close it." + Color.RESET);

        while (true) {
            String command = "";
            command = scanner.nextLine().trim();

            String[] args = command.split("\\s+");

            if (args.length == 0) {
                throw new InvalidCommandException(Color.RED_BOLD + "[Read Line]Invalid command" + Color.RESET);
            }

            if (command.equals("exit")) {
                break;
            }

            ShellCommand shellCommand = null;
            if (args[0].equals("add")) {
                shellCommand = new AddCommand();
                commandMessage = "MediaItem successfully added";
            } else if (args[0].equals("list")) {
                shellCommand = new ListCommand();
                commandMessage = "List successfully displayed";
            } else if (args[0].equals("play")) {
                shellCommand = new PlayCommand();
                commandMessage = "Media successfully played";
            } else if (args[0].equals("save")) {
                shellCommand = new SaveCommand();
                commandMessage = "This catalog was saved";
            } else if(args[0].equals("info")) {
                shellCommand = new InfoCommand();
                commandMessage = "The catalog metadata displayed successfully";
            } else if (args[0].equals("load")) {
                shellCommand = new LoadCommand();
                commandMessage = "The catalog was fetched";
            } else if(args[0].equals("report")) {
                shellCommand = new ReportCommand();
                commandMessage = "The report successfully generated";
            } else {
                throw new InvalidCommandException(Color.RED_BOLD + "Invalid command" + Color.RESET);
            }

            catalogLoaded = shellCommand.run(args, this);

            System.out.println(Color.YELLOW_BOLD + commandMessage + Color.RESET);
            System.out.println("");
        }

        return catalogLoaded;
    }
}
