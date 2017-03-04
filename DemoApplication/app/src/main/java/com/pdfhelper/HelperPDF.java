package com.pdfhelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import com.greencardgo.R;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;



@SuppressWarnings("ResultOfMethodCallIgnored")
public class HelperPDF {

    public static String actualPath (Activity activity) {

        String title;
        String folder;
        String path;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);

        title = sharedPref.getString("title", null);
        folder = sharedPref.getString("folder", "/Android/data/de.baumann.pdf/");
        path = sharedPref.getString("pathPDF", Environment.getExternalStorageDirectory() +
                folder + title + ".pdf");

        return path;
    }


    public static void pdf_backup (final Activity activity) {

        String title;
        String folder;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);

        if (sharedPref.getBoolean ("backup", false)){

            InputStream in;
            OutputStream out;

            try {
                title = sharedPref.getString("title", null);
                folder = sharedPref.getString("folder", "/Android/data/de.baumann.pdf/");

                in = new FileInputStream(HelperPDF.actualPath(activity));
                out = new FileOutputStream(Environment.getExternalStorageDirectory() +
                        folder + "pdf_backups/" + title + ".pdf");

                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
                in.close();

                // write the output file
                out.flush();
                out.close();
            } catch (Exception e) {
                Log.e("tag", e.getMessage());
            }
        }
    }

    public static void toolbar (final Activity activity) {

        String title;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        sharedPref.getString("rotateString", "portrait");

        title = sharedPref.getString("title", null);
        File pdfFile = new File(HelperPDF.actualPath(activity));

        if (pdfFile.exists()) {
            activity.setTitle(title);
        } else {
            activity.setTitle(R.string.app_name);
        }
    }


    public static void pdf_deleteTemp_1 (final Activity activity) {

        InputStream in;
        OutputStream out;

        try {

            in = new FileInputStream(Environment.getExternalStorageDirectory() +  "/" + "123456.pdf");
            out = new FileOutputStream(HelperPDF.actualPath(activity));

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();

            // write the output file
            out.flush();
            out.close();
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

        File pdfFile = new File(Environment.getExternalStorageDirectory() +  "/" + "123456.pdf");
        if(pdfFile.exists()){
            pdfFile.delete();
        }
    }

    public static void pdf_deleteTemp_2 (final Activity activity) {

        InputStream in;
        OutputStream out;

        try {

            in = new FileInputStream(Environment.getExternalStorageDirectory() +  "/" + "1234567.pdf");
            out = new FileOutputStream(HelperPDF.actualPath(activity));

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();

            // write the output file
            out.flush();
            out.close();
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

        File pdfFile = new File(Environment.getExternalStorageDirectory() +  "/" + "1234567.pdf");
        if(pdfFile.exists()){
            pdfFile.delete();
        }
    }

    private File createFile(){
        InputStream in;
        OutputStream out;
        String title="GreenCard";
        String folder =  "/Android/data/de.baumann.pdf/";
        String path =  Environment.getExternalStorageDirectory() +
                folder + title + ".pdf";
        try {

            in = new FileInputStream(Environment.getExternalStorageDirectory() +  "/" + title + ".pdf");
            out = new FileOutputStream(path);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();

            // write the output file
            out.flush();
            out.close();
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

        File pdfFile = new File(Environment.getExternalStorageDirectory() +  "/" + title + ".pdf");
        if(pdfFile.exists()){
            pdfFile.delete();
        }

        return pdfFile;
    }

    private boolean convertToPdf(String outputPdfPath,String paragraph) {
        try {

            // Create output file if needed
            File outputFile = new File(outputPdfPath);
            if (!outputFile.exists()) outputFile.createNewFile();

            Document document;

            document = new Document(PageSize.A4);


            PdfWriter.getInstance(document, new FileOutputStream(outputFile));
            document.open();
            document.add(new Paragraph(paragraph));

            document.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    private void sharePdf(Context mContext){
        File pdfFile=createFile();
        if (pdfFile.exists()) {


            Uri myUri= Uri.fromFile(pdfFile);
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("application/pdf");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, myUri);
//            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, FileTitle);
//            sharingIntent.putExtra(Intent.EXTRA_TEXT, text + " " + FileTitle);
            sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            startActivity(Intent.createChooser(sharingIntent, getString(R.string.action_share_with)));
        }
        else {
            Toast.makeText(mContext, "PDf file not cretaed", Toast.LENGTH_SHORT).show();
        }
    }

/*
    public static void pdf_mergePDF(Activity activity, View view) {

        String path2 = Environment.getExternalStorageDirectory() +  "/" + "123456.pdf";
        String path3 = Environment.getExternalStorageDirectory() +  "/" + "1234567.pdf";

        try {
            String[] files = { HelperPDF.actualPath(activity), path2 };
            Document document = new Document();
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(path3));
            document.open();
            PdfReader ReadInputPDF;
            int number_of_pages;
            for (String file : files) {
                ReadInputPDF = new PdfReader(file);
                number_of_pages = ReadInputPDF.getNumberOfPages();
                for (int page = 0; page < number_of_pages; ) {
                    copy.addPage(copy.getImportedPage(ReadInputPDF, ++page));
                }
            }
            document.close();
        } catch (Exception i) {
            Snackbar.make(view, activity.getString(R.string.toast_successfully_not), Snackbar.LENGTH_LONG).show();
        }
        HelperPDF.pdf_deleteTemp_1(activity);
    }

    public static void pdf_success (final Activity activity, View view) {

        Snackbar snackbar = Snackbar
                .make(view, activity.getString(R.string.toast_successfully), Snackbar.LENGTH_LONG)
                .setAction(activity.getString(R.string.toast_open), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        File file = new File(HelperPDF.actualPath(activity));
                        helper_main.openFile(activity, file, "application/pdf", view);
                    }
                });
        snackbar.show();
    }
*/
}
