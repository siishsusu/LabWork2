import java.io.*;

public class Files {
    void clearTxtFile(File file) {
        try {
            FileWriter writer = new FileWriter(file, false);
            writer.write("");
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    void createTxtGroups(String newGroup, File file){
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write(newGroup + "\n");
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    void createTxtProducts(String newProduct, File file){
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write(newProduct + "\n");
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    void editTxtGroups(String newGroup, String oldGroupName, File file){
        try {
            File groupsTemp = new File("groupsTemp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(groupsTemp));
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.contains(oldGroupName)) {
                    writer.write(newGroup + "\n");
                } else {
                    writer.write(currentLine + "\n");
                }
            }reader.close();writer.close();
            file.delete();
            groupsTemp.renameTo(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    void editTxtProducts(String newProduct, String oldProductName, File file){
        try {
            File groupsTemp = new File("groupsTemp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(groupsTemp));
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.contains(oldProductName)) {
                    writer.write(newProduct + "\n");
                } else {
                    writer.write(currentLine + "\n");
                }
            }reader.close();writer.close();
            file.delete();
            groupsTemp.renameTo(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
