import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress number1 = new GameProgress(20, 10, 5, 5.5);
        GameProgress number2 = new GameProgress(30, 20, 10, 7.7);
        GameProgress number3 = new GameProgress(40, 30, 15, 10.5);
        saveGame("C://Games//savegames//save1.dat", number1);
        saveGame("C://Games//savegames//save2.dat", number2);
        saveGame("C://Games//savegames//save3.dat", number3);

        List<File> fileList = Arrays.asList(new File("C://Games//savegames//save1.dat"),
                new File("C://Games//savegames//save2.dat"),
                new File("C://Games//savegames//save3.dat"));

        zipFiles("C://Games//savegames//zip.zip", fileList);

        for (File file : fileList) {
            deleteFile(file);
        }
    }

    public static void saveGame(String road, GameProgress gameProgress) {
        File file = new File(road);
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void zipFiles(String roadToZip, List<File> roads) {

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(roadToZip))) {
            int i = 1;
            for (File file : roads) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry("packed_game_progress" + i + ".txt");
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                i++;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void deleteFile(File file) {
        if (file.delete()) {
            System.out.println("Фаил " + file.getName() + " удален");
        } else {
            System.out.println("Фаил" + file.getName() + " не удален");
        }
    }
}