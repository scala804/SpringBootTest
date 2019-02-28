import java.io.*;

public class AutoCheckText {

    public  static void main(String[] args) {
        //取得目标目录
        File comparefile = new File("D:\\comparefile\\project");
        //获取目录下子文件及子文件夹
        File compareToFile = new File("D:\\compareToFile\\project");
        File[] files = comparefile.listFiles();
        creatDirs1(files);

    }
    public static void creatDirs1(File[] files) {
        if (files == null) {// 如果目录为空，直接退出
            return;
        }
        for (File compareFile : files) {
            String fileName = compareFile.getName();
            if (compareFile.isFile() && fileName.endsWith("properties")) {
                String filePath = "";
                filePath = compareFile.getAbsolutePath().substring(compareFile.getAbsolutePath().indexOf("\\", 3), compareFile.getAbsolutePath().lastIndexOf("\\"));
                File compareToFile = new File("D:\\compareToFile\\" + filePath + "\\" + fileName);
                String savefliePathName = "D:\\test" + filePath + "\\" + fileName;

                if (compareToFile.exists()){
                    File compareToSaveFilefile = new File(savefliePathName);
                    File fileParent = compareToSaveFilefile.getParentFile();
                    if  (!fileParent.exists()) {
                        //创建父目录文件
                        fileParent.mkdirs();
                    }
                    try {
                        compareToSaveFilefile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                   /* int add=2;
                    int delete=1;*/
                    //删除、修改
                    savefileToFileDir1(compareToFile, compareFile, savefliePathName);
                  /*//增加
                    savefileToFileDir1(add,compareFile, compareToFile, savefliePathName);*/
                }
            }
            //如果是文件夹，递归调用
            else if (compareFile.isDirectory()) {
                creatDirs1(compareFile.listFiles());
            }
        }

    }

    private static void savefileToFileDir1(File compareToFile, File compareFile, String savefliePathName) {

        BufferedReader br = null;
        BufferedReader cbr = null;
        BufferedWriter rbw = null;
        int addCompareType=1;
        int deleteCompareType=2;

        long startTime = System.currentTimeMillis();
        try {
            br = new BufferedReader(new FileReader(compareFile));
            cbr = new BufferedReader(new FileReader(compareToFile));
            cbr.mark(90000000);
            rbw = new BufferedWriter(new FileWriter(savefliePathName));
            String lineText = null;
            while ((lineText = br.readLine()) != null) {
                /*System.out.println(lineText);*/
                String searchText = lineText.trim();
                    searchAndSignProcess(deleteCompareType,searchText, cbr, rbw);
            }
           while ((lineText = cbr.readLine()) != null){
                String searchText = lineText.trim();
                    searchAndSignProcess(addCompareType,searchText, br, rbw);
            }
            long endTime = System.currentTimeMillis();
            System.out.println("======Process Over!=======");
            System.out.println("Time Spending:" + ((endTime - startTime) / 1000D) + "s");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (cbr != null && rbw != null) {
                        try {
                            cbr.close();
                            rbw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static void searchAndSignProcess(int compareTye,String searchText, BufferedReader comparedReader, BufferedWriter rbw)
            throws IOException {
        /*String lineStr = "\n";*/
        String deleteLineStr="删除\n";
        String modifyLineStr="修改\n";
        String addLineStr="增加\n";

        String lineStr="";
        if (searchText == null) {
            return;
        }
        if ("".equals(searchText)) {
            rbw.write(lineStr);
            return;
        }
        String lineText = null;
        int deleteLineNum = 1;
        int modifyLineNum=1;
        int addLineNum=1;
        while ((lineText = comparedReader.readLine()) != null&&searchText.indexOf("=")!=-1) {
            String searchTextKey = searchText.substring(0, searchText.indexOf("="));
            String comparedLine = lineText.trim();
            if(comparedLine.indexOf("=") != -1&&compareTye==2){
                String comparedLineKey=comparedLine.substring(0,comparedLine.indexOf("="));
            if (searchTextKey.equals(comparedLineKey) ) {
                deleteLineNum++;
                if (searchText.equals(comparedLine)) {
                    modifyLineNum++;
                    break;
                }
                break;
            }
         }
            if (comparedLine.indexOf("=") != -1&&compareTye==1){
                String comparedLineKey=comparedLine.substring(0,comparedLine.indexOf("="));
                if (searchTextKey.equals(comparedLineKey)){
                    addLineNum++;
                }
            }
        }
        if (deleteLineNum==1&&compareTye==2){
            deleteLineStr = deleteLineStr + searchText + "\n";
           /* System.out.println(addlineStr);*/
            /*rbw.write(deleteLineStr);*/
        }else if (modifyLineNum==1){
            modifyLineStr = modifyLineStr + searchText + "\n";
            /*System.out.println(modifylineStr);*/
            /*rbw.write(modifyLineStr);*/
        }
        if (addLineNum==1&&compareTye==1){
            addLineStr = addLineStr + searchText + "\n";
            /*System.out.println(modifylineStr);*/
            /*rbw.write(addLineStr);*/
        }
        rbw.write(deleteLineStr+modifyLineStr+addLineStr);

        comparedReader.mark(9000000);
    }

    /*public static void searchAndSignProcessDelete(String searchText, BufferedReader comparedReader, BufferedWriter rbw)
            throws IOException {
        *//*String lineStr = "\n";*//*
        String deleteLineStr="增加\n";
        String lineStr="";
        if (searchText == null) {
            return;
        }
        if ("".equals(searchText)) {
            rbw.write(lineStr);
            return;
        }
        String lineText = null;
        int deleteLineNum=1;
        String searchTextKey=searchText.substring(0,searchText.indexOf("="));
        while ((lineText = comparedReader.readLine()) != null&&(lineText.trim()).indexOf("=")!=-1) {
            String comparelineText = lineText.trim();
            String comparedLineKey=comparelineText.substring(0,comparelineText.indexOf("="));
            if (searchTextKey.equals(comparedLineKey)&&comparelineText.indexOf("=")!=-1) {
               System.out.println(searchText);
                deleteLineNum++;
                break;
            }

        }
        if (deleteLineNum==1){
            deleteLineStr = deleteLineStr + searchText + "\n";
          *//*  System.out.println(deleteLineStr);*//*
            rbw.write(deleteLineStr);
        }

        comparedReader.reset();
    }*/
}
