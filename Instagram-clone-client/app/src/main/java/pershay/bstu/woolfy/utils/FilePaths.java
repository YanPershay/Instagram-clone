package pershay.bstu.woolfy.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FilePaths {

    public  String ROOT_DIR = Environment.getExternalStorageDirectory().getPath();
    public String PICTURES = ROOT_DIR;// + "/Pictures";
    //public String CAMERA = ROOT_DIR + "/DCIM/Camera";
    public String DOWNLOAD = ROOT_DIR;// + "/Download";
    //public String VKDOWNLOADS = ROOT_DIR + "/VK/Downloads";
}
