package com.pdfhelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.Util.GCGModesParser;
import com.greencardgo.BuildConfig;
import com.greencardgo.R;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


@SuppressWarnings("ResultOfMethodCallIgnored")
public class HelperPDF {

    public static String actualPath(Activity activity) {

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


    public static void pdf_backup(final Activity activity) {

        String title;
        String folder;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);

        if (sharedPref.getBoolean("backup", false)) {

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

    public static void toolbar(final Activity activity) {

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


    public static void pdf_deleteTemp_1(final Activity activity) {

        InputStream in;
        OutputStream out;

        try {

            in = new FileInputStream(Environment.getExternalStorageDirectory() + "/" + "123456.pdf");
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

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + "123456.pdf");
        if (pdfFile.exists()) {
            pdfFile.delete();
        }
    }

    public static void pdf_deleteTemp_2(final Activity activity) {

        InputStream in;
        OutputStream out;

        try {

            in = new FileInputStream(Environment.getExternalStorageDirectory() + "/" + "1234567.pdf");
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

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + "1234567.pdf");
        if (pdfFile.exists()) {
            pdfFile.delete();
        }
    }

    private static File createFile() {
//        InputStream in;
//        OutputStream out;
        String title = "GreenCard";

//        String folder =  "/Android/data/de.baumann.pdf/";
//        String path =  Environment.getExternalStorageDirectory() +
//                folder + title + ".pdf";
//        try {
//
//            in = new FileInputStream(Environment.getExternalStorageDirectory() +  "/" + title + ".pdf");
//            out = new FileOutputStream(path);
//
//            byte[] buffer = new byte[1024];
//            int read;
//            while ((read = in.read(buffer)) != -1) {
//                out.write(buffer, 0, read);
//            }
//            in.close();
//
//            // write the output file
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            Log.e("tag", e.getMessage());
//        }

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + title + ".pdf");
        if (pdfFile.exists()) {
            pdfFile.delete();
        }

        return pdfFile;
    }

    private static boolean convertToPdf(File outputPdfPath, String paragraph) {
        File outputFile = outputPdfPath;
        // step 1
        Document document = new Document(PageSize.A4);
        // step 2
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(outputFile));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        TableHeader event = new TableHeader();
//        writer.setPageEvent(event);
        // step 3 - fill in the document
        document.open();


//        event.setHeader("Green Card report");
        try {
            PdfContentByte cb = writer.getDirectContent();

            Font ffont1 = new Font(Font.FontFamily.UNDEFINED, 30, Font.NORMAL);
            Phrase header = new Phrase("GreenCard Go! Report", ffont1);
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    header,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.top(), 0);

            LineSeparator UNDERLINE =
                    new LineSeparator(2, 100, null, Element.ALIGN_CENTER, 0);
            UNDERLINE.drawLine(cb, 0, 0, document.top() - 80);
            try {
                document.add(new Paragraph("\n"));
                document.add(UNDERLINE);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            document.add(new Paragraph("\n"));

            Font ffont = new Font(Font.FontFamily.UNDEFINED, 15, Font.NORMAL);
//            Chunk c = new Chunk("Hi Tetsing", ffont);\

            Chunk c = new Chunk(addSummaryBeforeSteps(), ffont);
            Paragraph p1 = new Paragraph(c);
            document.add(p1);

            document.add(UNDERLINE);

            StringBuilder builder = new StringBuilder();


            builder.append("\nBelow are all the actual responses that you've inputted into the app and the action steps given:"
                    + "\n\n" + paragraph);

            Chunk c1 = new Chunk(builder.toString(), ffont);
            Paragraph p2 = new Paragraph(c1);
            document.add(p2);

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document.close();

//        try {
//
//            // Create output file if needed
//            File outputFile = outputPdfPath;
//            if (!outputFile.exists()) outputFile.createNewFile();
//
//            Document document;
//
//            document = new Document(PageSize.A4, 36, 36, 54, 36);
//
//            PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream(outputFile));
//            document.open();
//            TableHeader event=new TableHeader();
//            writer.setPageEvent(event);
//            event.setHeader("Green Card report");
//            document.add(new Paragraph("Hi Tetsing"));
//
//            document.close();
//
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        return false;
    }


    public static void sharePdf(String paragraph, Context mContext) {
        File pdfFile = createFile();
        convertToPdf(pdfFile, paragraph);
        if (pdfFile.exists()) {


//            Uri myUri = Uri.fromFile(pdfFile);

            Uri myUri = FileProvider.getUriForFile(mContext,
                    BuildConfig.APPLICATION_ID + ".provider",
                    pdfFile);
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("application/pdf");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, myUri);
//            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, FileTitle);
//            sharingIntent.putExtra(Intent.EXTRA_TEXT, text + " " + FileTitle);
            sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            mContext.startActivity(sharingIntent);
        }
//        else {
////            Toast.makeText(mContext, "PDf file not cretaed", Toast.LENGTH_SHORT).show();
//        }
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


    static class TableHeader extends PdfPageEventHelper {
        /**
         * The header text.
         */
        String header;
        /**
         * The template with the total number of pages.
         */
        PdfTemplate total;

        /**
         * Allows us to change the content of the header.
         *
         * @param header The new header String
         */
        public void setHeader(String header) {
            this.header = header;
        }

        /**
         * Creates the PdfTemplate that will hold the total number of pages.
         *
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
         *com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onOpenDocument(PdfWriter writer, Document document) {
            total = writer.getDirectContent().createTemplate(30, 16);
        }

        /**
         * Adds a header to every page
         *
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
         *com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onStartPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            Font ffont = new Font(Font.FontFamily.UNDEFINED, 25, Font.NORMAL);
            Phrase header = new Phrase("GreenCard Go! Report", ffont);
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    header,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.top(), 0);

            /*LineSeparator UNDERLINE =
                    new LineSeparator(10, 100, null, Element.ALIGN_CENTER, 0);
            UNDERLINE.drawLine(cb,0,0,10);
            try {
                document.add(UNDERLINE);
            } catch (DocumentException e) {
                e.printStackTrace();
            }*/
          /*  PdfPCell myCell = new PdfPCell(new Paragraph("Hello World") );
            myCell.setBorder(Rectangle.BOTTOM);
            table.addCell(myCell);*/
//            try {
//                table.setWidths(new int[]{40, 40, 2});
//                table.setTotalWidth(527);
//                table.setLockedWidth(true);
//                table.getDefaultCell().setFixedHeight(20);
//                table.getDefaultCell().setBorder(Rectangle.ALIGN_CENTER);
//                table.addCell(header);
//                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
////                table.addCell(String.format("Page %d of", writer.getPageNumber()));
//                PdfPCell cell = new PdfPCell(Image.getInstance(total));
//                cell.setBorder(Rectangle.BOTTOM);
//                table.addCell(cell);
//                table.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
//            }
//            catch(DocumentException de) {
//                throw new ExceptionConverter(de);
//            }
        }


        /**
         * Fills out the total number of pages before the document is closed.
         *
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(
         *com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onCloseDocument(PdfWriter writer, Document document) {
          /*  ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
                    new Phrase(String.valueOf(writer.getPageNumber() - 1)),
                    2, 2, 0);*/
        }
    }


    private static String printForMode1(StringBuilder buider) {


        if (GCGModesParser.arrQuestionaireSelected.contains("Option1")) {
//            buider.append("- Because you're option 1, you can:  OV6\n");


            if (GCGModesParser.dicStateMode.containsKey("M1_OV1_OPTION1") && GCGModesParser.dicStateMode.get("M1_OV1_OPTION1") != null) {
                buider.append("- Because you're option 1, you can:  "+getStateModeForPdfValue("M1_OV1_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("M1_OV2_TITLE") && GCGModesParser.dicStateModeForPdf.get("M1_OV2_TITLE") != null) {
                buider.append("- Because you're option 1, you can:  "+getStateModeForPdfValue("M1_OV2_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateMode.containsKey("M1_OV4_OPTION1") && GCGModesParser.dicStateMode.get("M1_OV4_OPTION1") != null) {
                buider.append("- Because you're option 1, you can:  "+getStateModeForPdfValue("M1_OV4_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateMode.containsKey("M1_OV5_OPTION1") && GCGModesParser.dicStateMode.get("M1_OV5_OPTION1") != null) {
                buider.append("- Because you're option 1, you can:  "+getStateModeForPdfValue("M1_OV5_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateMode.containsKey("M1_OV1_OPTION6") && GCGModesParser.dicStateMode.get("M1_OV1_OPTION6") != null) {
                buider.append("- Because you're option 1, you can:  "+getStateModeForPdfValue("M1_OV6_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("P41_OV1_TITLE") && GCGModesParser.dicStateModeForPdf.get("P41_OV1_TITLE") != null) {
                buider.append("- Because you're option 1, you can:  "+getStateModeForPdfValue("P41_OV1_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("P41_OV2_TITLE") && GCGModesParser.dicStateModeForPdf.get("P41_OV2_TITLE") != null) {
                buider.append("- Because you're option 1, you can:  "+getStateModeForPdfValue("P41_OV2_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("P41_OV3_TITLE") && GCGModesParser.dicStateModeForPdf.get("P41_OV3_TITLE") != null) {
                buider.append("- Because you're option 1, you can:  "+getStateModeForPdfValue("P41_OV3_TITLE")+"\n");
            }

        }

        if (GCGModesParser.arrQuestionaireSelected.contains("Option2")) {
//            buider.append("\n- Because you're option 2, you can:  OV3 [or OV7]\n");

            if (GCGModesParser.dicStateMode.containsKey("M1_OV1_OPTION2") && GCGModesParser.dicStateMode.get("M1_OV1_OPTION2") != null) {
                buider.append("- Because you're option 2, you can:  "+getStateModeForPdfValue("M1_OV1_TITLE")+"\n");
            }

            if (GCGModesParser.dicStateModeForPdf.containsKey("M1_OV3_TITLE") && GCGModesParser.dicStateModeForPdf.get("M1_OV3_TITLE") != null) {
                buider.append("- Because you're option 2, you can:  "+getStateModeForPdfValue("M1_OV3_TITLE")+"\n");
            }

        }

        if (GCGModesParser.arrQuestionaireSelected.contains("Option3")) {

            if (GCGModesParser.dicStateMode.containsKey("M1_OV1_OPTION3") && GCGModesParser.dicStateMode.get("M1_OV1_OPTION3") != null) {
                buider.append("- Because you're option 3, you can:  "+getStateModeForPdfValue("M1_OV1_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("M1_OV4_TITLE") && GCGModesParser.dicStateModeForPdf.get("M1_OV4_TITLE") != null) {
                buider.append("- Because you're option 3, you can:  "+getStateModeForPdfValue("M1_OV4_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("M1_OV5_TITLE") && GCGModesParser.dicStateModeForPdf.get("M1_OV5_TITLE") != null) {
                buider.append("- Because you're option 3, you can:  "+getStateModeForPdfValue("M1_OV5_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("M1_OV6_TITLE") && GCGModesParser.dicStateModeForPdf.get("M1_OV6_TITLE") != null) {
                buider.append("- Because you're option 3, you can:  "+getStateModeForPdfValue("M1_OV6_TITLE")+"\n");
            }

            if (GCGModesParser.dicStateModeForPdf.containsKey("M1_OC3_TITLE")
                    && GCGModesParser.dicStateModeForPdf.get("M1_OC3_TITLE").equalsIgnoreCase("M1_OC3_OPTIONC")) {

                if (GCGModesParser.dicStateModeForPdf.containsKey("P61_D1_TITLE") && GCGModesParser.dicStateModeForPdf.get("P61_D1_TITLE") != null) {
                    if (GCGModesParser.dicStateModeForPdf.containsKey("P61_OV1_TITLE") && GCGModesParser.dicStateModeForPdf.get("P61_OV1_TITLE") != null) {
                        buider.append("- Because you're P61, you can:  "+getStateModeForPdfValue("P61_OV1_TITLE")+"\n");
                    }
                    if (GCGModesParser.dicStateModeForPdf.containsKey("P61_OV2_TITLE") && GCGModesParser.dicStateModeForPdf.get("P61_OV2_TITLE") != null) {
                        buider.append("- Because you're P61, you can:  "+getStateModeForPdfValue("P61_OV2_TITLE")+"\n");
                    }
                    if (GCGModesParser.dicStateModeForPdf.containsKey("P61_OV3_TITLE") && GCGModesParser.dicStateModeForPdf.get("P61_OV3_TITLE") != null) {
                        buider.append("- Because you're P61, you can:  "+getStateModeForPdfValue("P61_OV3_TITLE")+"\n");
                    }
                    if (GCGModesParser.dicStateModeForPdf.containsKey("P61_OV4_TITLE") && GCGModesParser.dicStateModeForPdf.get("P61_OV4_TITLE") != null) {
                        buider.append("- Because you're P61, you can:  "+getStateModeForPdfValue("P61_OV4_TITLE")+"\n");
                    }
                }

                else{
                    if (GCGModesParser.dicStateModeForPdf.containsKey("P61_OV1_TITLE") && GCGModesParser.dicStateModeForPdf.get("P61_OV1_TITLE") != null) {
                        buider.append("- Because you're option 3, you can:  "+getStateModeForPdfValue("P61_OV1_TITLE")+"\n");
                    }
                    if (GCGModesParser.dicStateModeForPdf.containsKey("P61_OV2_TITLE") && GCGModesParser.dicStateModeForPdf.get("P61_OV2_TITLE") != null) {
                        buider.append("- Because you're option 3, you can:  "+getStateModeForPdfValue("P61_OV2_TITLE")+"\n");
                    }
                    if (GCGModesParser.dicStateModeForPdf.containsKey("P61_OV3_TITLE") && GCGModesParser.dicStateModeForPdf.get("P61_OV3_TITLE") != null) {
                        buider.append("- Because you're option 3, you can:  "+getStateModeForPdfValue("P61_OV3_TITLE")+"\n");
                    }
                    if (GCGModesParser.dicStateModeForPdf.containsKey("P61_OV4_TITLE") && GCGModesParser.dicStateModeForPdf.get("P61_OV4_TITLE") != null) {
                        buider.append("- Because you're option 3, you can:  "+getStateModeForPdfValue("P61_OV4_TITLE")+"\n");
                    }
                }
            }

        }


        if (GCGModesParser.arrQuestionaireSelected.contains("Option4")) {


            if (GCGModesParser.dicStateMode.containsKey("M1_D7_H8") && GCGModesParser.dicStateMode.get("M1_D7_H8").equalsIgnoreCase("yes")) {
                if (GCGModesParser.dicStateModeForPdf.containsKey("M1_OV9_TITLE") && GCGModesParser.dicStateModeForPdf.get("M1_OV9_TITLE") != null) {
                    buider.append("- Because you're option 4, you can:  "+getStateModeForPdfValue("M1_OV9_TITLE")+"\n");
                }
            } else {
                if (GCGModesParser.dicStateModeForPdf.containsKey("M1_D2_TITLE") && GCGModesParser.dicStateModeForPdf.get("M1_D2_TITLE").equalsIgnoreCase("yes")) {
//                    if (GCGModesParser.dicStateModeForPdf.containsKey("M1_OV8_TITLE") && GCGModesParser.dicStateModeForPdf.get("M1_OV8_TITLE") != null) {
                        buider.append("- Because you're option 4, you can:  "+getStateModeForPdfValue("M1_OV8_TITLE")+"\n");
//                    }
                } else {
//                    if (GCGModesParser.dicStateModeForPdf.containsKey("M1_OV7_TITLE") && GCGModesParser.dicStateModeForPdf.get("M1_OV7_TITLE") != null) {
                        buider.append("- Because you're option 4, you can: "+getStateModeForPdfValue("M1_OV7_TITLE")+"\n");
//                    }
                }
            }
        }


        if (GCGModesParser.dicStateModeForPdf.containsKey("M1_D9_TITLE") && GCGModesParser.dicStateModeForPdf.get("M1_D9_TITLE").equalsIgnoreCase("yes")) {


            if (GCGModesParser.dicStateMode.containsKey("M1_D7_H7") && GCGModesParser.dicStateMode.get("M1_D7_H7").equalsIgnoreCase("yes")) {

                if (GCGModesParser.dicStateModeForPdf.containsKey("M1_OV9_TITLE") && GCGModesParser.dicStateModeForPdf.get("M1_OV9_TITLE") != null) {
                    buider.append("- Because you have M1_D9, you can:  "+getStateModeForPdfValue("M1_OV9_TITLE")+"\n");
                }
            } else {

                if (GCGModesParser.dicStateModeForPdf.containsKey("M1_D2_TITLE") && GCGModesParser.dicStateModeForPdf.get("M1_D2_TITLE").equalsIgnoreCase("yes")) {

//                    if (GCGModesParser.dicStateModeForPdf.containsKey("M1_OV8_TITLE") && GCGModesParser.dicStateModeForPdf.get("M1_OV8_TITLE") != null) {
                        buider.append("- Because you have M1_D9, you can:  "+getStateModeForPdfValue("M1_OV8_TITLE")+"\n");
//                    }
                } else {

//                    if (GCGModesParser.dicStateModeForPdf.containsKey("M1_OV7_TITLE") && GCGModesParser.dicStateModeForPdf.get("M1_OV7_TITLE") != null) {
                        buider.append("- Because you have M1_D9, you can:  "+getStateModeForPdfValue("M1_OV7_TITLE")+"\n");
//                    }
                }

            }


        }


        if ((GCGModesParser.dicStateModeForPdf.containsKey("P51_D1_TITLE") && GCGModesParser.dicStateModeForPdf.get("P51_D1_TITLE") != null)
                ||
                (GCGModesParser.dicStateModeForPdf.containsKey("P51_H10_TITLE") && GCGModesParser.dicStateModeForPdf.get("P51_H10_TITLE") != null)
                ||
                (GCGModesParser.dicStateModeForPdf.containsKey("P51_H11_TITLE") && GCGModesParser.dicStateModeForPdf.get("P51_H11_TITLE") != null)
                )

        {
//            buider.append("\n- Because you're P52, you can:  P52_OV7\n");
            if (GCGModesParser.dicStateModeForPdf.containsKey("P51_OV1_TITLE") && GCGModesParser.dicStateModeForPdf.get("P51_OV1_TITLE") != null) {
                buider.append("- Because you're P51, you can:  "+getStateModeForPdfValue("P51_OV1_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("P51_OV2_TITLE") && GCGModesParser.dicStateModeForPdf.get("P51_OV2_TITLE") != null) {
                buider.append("- Because you're P51, you can:  "+getStateModeForPdfValue("P51_OV2_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("P51_OV3_TITLE") && GCGModesParser.dicStateModeForPdf.get("P51_OV3_TITLE") != null) {
                buider.append("- Because you're P51, you can:  "+getStateModeForPdfValue("P51_OV3_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("P51_OV4_TITLE") && GCGModesParser.dicStateModeForPdf.get("P51_OV4_TITLE") != null) {
                buider.append("- Because you're P51, you can:  "+getStateModeForPdfValue("P51_OV4_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("P51_OV5_TITLE") && GCGModesParser.dicStateModeForPdf.get("P51_OV5_TITLE") != null) {
                buider.append("- Because you're P51, you can:  "+getStateModeForPdfValue("P51_OV5_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("P51_OV6_TITLE") && GCGModesParser.dicStateModeForPdf.get("P51_OV6_TITLE") != null) {
                buider.append("- Because you're P51, you can:  "+getStateModeForPdfValue("P51_OV6_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("P51_OV7_TITLE") && GCGModesParser.dicStateModeForPdf.get("P51_OV7_TITLE") != null) {
                buider.append("- Because you're P51, you can:  "+getStateModeForPdfValue("P51_OV7_TITLE")+"\n");
            }

        }


        if (GCGModesParser.dicStateModeForPdf.containsKey("P61_D1_TITLE") && GCGModesParser.dicStateModeForPdf.get("P61_D1_TITLE") != null) {
            if (!GCGModesParser.dicStateModeForPdf.containsKey("M1_OC3_TITLE") || !GCGModesParser.dicStateModeForPdf.get("M1_OC3_TITLE").equalsIgnoreCase("M1_OC3_OPTIONC")) {
                if (GCGModesParser.dicStateModeForPdf.containsKey("P61_OV1_TITLE") && GCGModesParser.dicStateModeForPdf.get("P61_OV1_TITLE") != null) {
                    buider.append("- Because you're P61, you can:  "+getStateModeForPdfValue("P61_OV1_TITLE")+"\n");
                }
                if (GCGModesParser.dicStateModeForPdf.containsKey("P61_OV2_TITLE") && GCGModesParser.dicStateModeForPdf.get("P61_OV2_TITLE") != null) {
                    buider.append("- Because you're P61, you can:  "+getStateModeForPdfValue("P61_OV2_TITLE")+"\n");
                }

                if (GCGModesParser.dicStateModeForPdf.containsKey("P61_OV3_TITLE") && GCGModesParser.dicStateModeForPdf.get("P61_OV3_TITLE") != null) {
                    buider.append("- Because you're P61, you can:  "+getStateModeForPdfValue("P61_OV3_TITLE")+"\n");
                }

                if (GCGModesParser.dicStateModeForPdf.containsKey("P61_OV4_TITLE") && GCGModesParser.dicStateModeForPdf.get("P61_OV4_TITLE") != null) {
                    buider.append("- Because you're P61, you can:  "+getStateModeForPdfValue("P61_OV4_TITLE")+"\n");
                }
            }
        }

        buider.append("\n");
        return buider.toString();
    }


    static void printForMode2(StringBuilder buider) {


        if (GCGModesParser.arrQuestionaireSelected.contains("Option1")) {

            if (GCGModesParser.dicStateMode.containsKey("OV1_OC1AD3") && GCGModesParser.dicStateMode.get("OV1_OC1AD3") != null
                    ||
                    GCGModesParser.dicStateMode.containsKey("OV1_H1BD3") && GCGModesParser.dicStateMode.get("OV1_H1BD3") != null
                    ) {
                buider.append("- Because you're option 1, you can:  "+getStateModeForPdfValue("OV1_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateMode.containsKey("OV2_H1AD2") && GCGModesParser.dicStateMode.get("OV2_H1AD2") != null) {
                buider.append("- Because you're option 1, you can:  "+getStateModeForPdfValue("OV2_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateMode.containsKey("OV3_H1BD2") && GCGModesParser.dicStateMode.get("OV3_H1BD2") != null
                    ||
                    GCGModesParser.dicStateMode.containsKey("OV3_OC1AD2") && GCGModesParser.dicStateMode.get("OV3_OC1AD2") != null

                    ) {
                buider.append("- Because you're option 1, you can:  "+getStateModeForPdfValue("OV3_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("OV5_TITLE") && GCGModesParser.dicStateModeForPdf.get("OV5_TITLE") != null) {
                buider.append("- Because you're option 1, you can:  "+getStateModeForPdfValue("OV5_TITLE")+"\n");
            }


            if (GCGModesParser.dicStateMode.containsKey("OV6_D6") && GCGModesParser.dicStateMode.get("OV6_D6") != null
                    ||
                    GCGModesParser.dicStateMode.containsKey("OV6_H1") && GCGModesParser.dicStateMode.get("OV6_H1") != null
                    ||
                    GCGModesParser.dicStateMode.containsKey("OV6_OC1") && GCGModesParser.dicStateMode.get("OV6_OC1") != null
                    ) {
                buider.append("- Because you're option 1, you can:  "+getStateModeForPdfValue("OV6_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("P42_OV1_TITLE") && GCGModesParser.dicStateModeForPdf.get("P42_OV1_TITLE") != null) {
                buider.append("- Because you're option 1, you can:  "+getStateModeForPdfValue("P42_OV1_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("P42_OV2_TITLE") && GCGModesParser.dicStateModeForPdf.get("P41_OV2_TITLE") != null) {
                buider.append("- Because you're option 1, you can:  "+getStateModeForPdfValue("P42_OV2_TITLE")+"\n");
            }
           /* if (GCGModesParser.dicStateModeForPdf.containsKey("P42_OV2_TITLE") && GCGModesParser.dicStateModeForPdf.get("P42_OV2_TITLE") != null) {
                buider.append("- Because you're option 1, you can:  P42_OV2_TITLE\n");
            }*/

        }

        if (GCGModesParser.arrQuestionaireSelected.contains("Option2")) {

            if (GCGModesParser.dicStateMode.containsKey("OV1_OC2AD3") && GCGModesParser.dicStateMode.get("OV1_OC2AD3") != null
                    ||
                    GCGModesParser.dicStateMode.containsKey("OV1_H3D3") && GCGModesParser.dicStateMode.get("OV1_H3D3") != null
                    ||
                    GCGModesParser.dicStateMode.containsKey("OV1_H4D3") && GCGModesParser.dicStateMode.get("OV1_H4D3") != null
                    ) {
                buider.append("- Because you're option 2, you can:  "+getStateModeForPdfValue("OV1_TITLE")+"\n");
            }

            if (GCGModesParser.dicStateMode.containsKey("OV3_OC2AD2") && GCGModesParser.dicStateMode.get("OV3_OC2AD2") != null
                    ||
                    GCGModesParser.dicStateMode.containsKey("OV3_H3D2") && GCGModesParser.dicStateMode.get("OV3_H3D2") != null
                    ||
                    GCGModesParser.dicStateMode.containsKey("OV3_H4D2") && GCGModesParser.dicStateMode.get("OV3_H4D2") != null
                    ) {
                buider.append("- Because you're option 2, you can:  "+getStateModeForPdfValue("OV3_TITLE")+"\n");
            }

            if (GCGModesParser.dicStateMode.containsKey("OV7_OC2") && GCGModesParser.dicStateMode.get("OV7_OC2") != null
                    ||
                    GCGModesParser.dicStateMode.containsKey("OV7_H3") && GCGModesParser.dicStateMode.get("OV7_H3") != null
                    ||
                    GCGModesParser.dicStateMode.containsKey("OV7_H4") && GCGModesParser.dicStateMode.get("OV7_H4") != null
                    ) {
                buider.append("- Because you're option 2, you can:  "+getStateModeForPdfValue("OV7_TITLE")+"\n");
            }

        }


        if (GCGModesParser.arrQuestionaireSelected.contains("Option3")) {

            if (GCGModesParser.dicStateMode.containsKey("OV1_OC3AD3") && GCGModesParser.dicStateMode.get("OV1_OC3AD3") != null
                    ||
                    GCGModesParser.dicStateMode.containsKey("OV1_D8D3") && GCGModesParser.dicStateMode.get("OV1_D8D3") != null
                    ) {
                buider.append("- Because you're option 3, you can:  "+getStateModeForPdfValue("OV1_TITLE")+"\n");
            }

            if (GCGModesParser.dicStateMode.containsKey("OV2_D9D2") && GCGModesParser.dicStateMode.get("OV2_D9D2") != null

                    ) {
                buider.append("- Because you're option 3, you can:  "+getStateModeForPdfValue("OV2_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateMode.containsKey("OV3_OC3AD2") && GCGModesParser.dicStateMode.get("OV3_OC3AD2") != null
                    ||

                    GCGModesParser.dicStateMode.containsKey("OV3_D8D2") && GCGModesParser.dicStateMode.get("OV3_D8D2") != null
                    ) {
                buider.append("- Because you're option 3, you can:  "+getStateModeForPdfValue("OV3_TITLE")+"\n");
            }


            if (GCGModesParser.dicStateMode.containsKey("OV8_D8") && GCGModesParser.dicStateMode.get("OV8_D8") != null
                    ||

                    GCGModesParser.dicStateMode.containsKey("OV8_D9") && GCGModesParser.dicStateMode.get("OV8_D9") != null
                    ||
                    GCGModesParser.dicStateMode.containsKey("OV8_D10") && GCGModesParser.dicStateMode.get("OV8_D10") != null
                    ||
                    GCGModesParser.dicStateMode.containsKey("OV8_D14") && GCGModesParser.dicStateMode.get("OV8_D14") != null
                    ||
                    GCGModesParser.dicStateMode.containsKey("OV8_OC3") && GCGModesParser.dicStateMode.get("OV8_OC3") != null

                    ) {
                buider.append("- Because you're option 3, you can:  "+getStateModeForPdfValue("OV8_TITLE")+"\n");
            }

           /* if (GCGModesParser.dicStateModeForPdf.containsKey("OV7_TITLE") && GCGModesParser.dicStateModeForPdf.get("OV7_TITLE") != null) {
                buider.append("- Because you're option 3, you can:  OV7_TITLE\n");
            }*/

            if (GCGModesParser.dicStateModeForPdf.containsKey("OC3_TITLE") && GCGModesParser.dicStateModeForPdf.get("OC3_TITLE").equalsIgnoreCase("OC3_OPTIONC")) {
                if (GCGModesParser.dicStateModeForPdf.containsKey("P62_D1_TITLE") && GCGModesParser.dicStateModeForPdf.get("P62_D1_TITLE") != null) {
                    if (GCGModesParser.dicStateModeForPdf.containsKey("P62_OV1_TITLE") && GCGModesParser.dicStateModeForPdf.get("P62_OV1_TITLE") != null) {
                        buider.append("- Because you're P62, you can:  "+getStateModeForPdfValue("P62_OV1_TITLE")+"\n");
                    }
                    if (GCGModesParser.dicStateModeForPdf.containsKey("P62_OV2_TITLE") && GCGModesParser.dicStateModeForPdf.get("P62_OV2_TITLE") != null) {
                        buider.append("- Because you're P62, you can:  "+getStateModeForPdfValue("P62_OV2_TITLE")+"\n");
                    }
                    if (GCGModesParser.dicStateModeForPdf.containsKey("P62_OV3_TITLE") && GCGModesParser.dicStateModeForPdf.get("P62_OV3_TITLE") != null) {
                        buider.append("- Because you're P62, you can:  "+getStateModeForPdfValue("P62_OV3_TITLE")+"\n");
                    }
                }

                else{
                    if (GCGModesParser.dicStateModeForPdf.containsKey("P62_OV1_TITLE") && GCGModesParser.dicStateModeForPdf.get("P62_OV1_TITLE") != null) {
                        buider.append("- Because you're option 3, you can:  "+getStateModeForPdfValue("P62_OV1_TITLE")+"\n");
                    }
                    if (GCGModesParser.dicStateModeForPdf.containsKey("P62_OV2_TITLE") && GCGModesParser.dicStateModeForPdf.get("P62_OV2_TITLE") != null) {
                        buider.append("- Because you're option 3, you can:  "+getStateModeForPdfValue("P62_OV2_TITLE")+"\n");
                    }
                    if (GCGModesParser.dicStateModeForPdf.containsKey("P62_OV3_TITLE") && GCGModesParser.dicStateModeForPdf.get("P62_OV3_TITLE") != null) {
                        buider.append("- Because you're option 3, you can:  "+getStateModeForPdfValue("P62_OV3_TITLE")+"\n");
                    }
                }
            }

        }

        if (GCGModesParser.arrQuestionaireSelected.contains("Option4")) {
            if (GCGModesParser.dicStateModeForPdf.containsKey("OV4_TITLE") && GCGModesParser.dicStateModeForPdf.get("OV4_TITLE") != null) {
                buider.append("- Because you're option 4, you can: "+getStateModeForPdfValue("OV4_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("OV9_TITLE") && GCGModesParser.dicStateModeForPdf.get("OV9_TITLE") != null) {
                buider.append("- Because you're option 4, you can:  "+getStateModeForPdfValue("OV9_TITLE")+"\n");
            }

        }

        if (GCGModesParser.dicStateModeForPdf.containsKey("D11_TITLE") && GCGModesParser.dicStateModeForPdf.get("D11_TITLE").equalsIgnoreCase("yes")) {
            if (GCGModesParser.dicStateModeForPdf.containsKey("OV4_TITLE") && GCGModesParser.dicStateModeForPdf.get("OV4_TITLE") != null) {
                buider.append("- Because you have D11, you can: "+getStateModeForPdfValue("OV4_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("OV9_TITLE") && GCGModesParser.dicStateModeForPdf.get("OV9_TITLE") != null) {
                buider.append("- Because you have D11, you can:  "+getStateModeForPdfValue("OV9_TITLE")+"\n");
            }
        }


        if ((GCGModesParser.dicStateModeForPdf.containsKey("P52_D1_TITLE") && GCGModesParser.dicStateModeForPdf.get("P52_D1_TITLE") != null)
                ||
                (GCGModesParser.dicStateModeForPdf.containsKey("P52_H11_TITLE") && GCGModesParser.dicStateModeForPdf.get("P52_H11_TITLE") != null)
                ||
                (GCGModesParser.dicStateModeForPdf.containsKey("P52_H12_TITLE") && GCGModesParser.dicStateModeForPdf.get("P52_H12_TITLE") != null)
                ) {
            if (GCGModesParser.dicStateModeForPdf.containsKey("P52_OV1_TITLE") && GCGModesParser.dicStateModeForPdf.get("P52_OV1_TITLE") != null) {
                buider.append("- Because you're P52, you can:  "+getStateModeForPdfValue("P52_OV1_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("P52_OV2_TITLE") && GCGModesParser.dicStateModeForPdf.get("P52_OV2_TITLE") != null) {
                buider.append("- Because you're P52, you can:  "+getStateModeForPdfValue("P52_OV2_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("P52_OV3_TITLE") && GCGModesParser.dicStateModeForPdf.get("P52_OV3_TITLE") != null) {
                buider.append("- Because you're P52, you can:  "+getStateModeForPdfValue("P52_OV3_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("P52_OV4_TITLE") && GCGModesParser.dicStateModeForPdf.get("P52_OV4_TITLE") != null) {
                buider.append("- Because you're P52, you can:  "+getStateModeForPdfValue("P52_OV4_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("P52_OV5_TITLE") && GCGModesParser.dicStateModeForPdf.get("P52_OV5_TITLE") != null) {
                buider.append("- Because you're P52, you can:  "+getStateModeForPdfValue("P52_OV5_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("P52_OV6_TITLE") && GCGModesParser.dicStateModeForPdf.get("P52_OV6_TITLE") != null) {
                buider.append("- Because you're P52, you can:  "+getStateModeForPdfValue("P52_OV6_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("P52_OV7_TITLE") && GCGModesParser.dicStateModeForPdf.get("P52_OV7_TITLE") != null) {
                buider.append("- Because you're P52, you can:  "+getStateModeForPdfValue("P52_OV7_TITLE")+"\n");
            }

        }


        if (GCGModesParser.dicStateModeForPdf.containsKey("P62_D1_TITLE") && GCGModesParser.dicStateModeForPdf.get("P62_D1_TITLE") != null) {
            if (!GCGModesParser.dicStateModeForPdf.containsKey("OC3_TITLE") || !GCGModesParser.dicStateModeForPdf.get("OC3_TITLE").equalsIgnoreCase("OC3_OPTIONC")) {
                if (GCGModesParser.dicStateModeForPdf.containsKey("P62_OV1_TITLE") && GCGModesParser.dicStateModeForPdf.get("P62_OV1_TITLE") != null) {
                    buider.append("- Because you're P62, you can:  "+getStateModeForPdfValue("P62_OV1_TITLE")+"\n");
                }
                if (GCGModesParser.dicStateModeForPdf.containsKey("P62_OV2_TITLE") && GCGModesParser.dicStateModeForPdf.get("P62_OV2_TITLE") != null) {
                    buider.append("- Because you're P62, you can:  "+getStateModeForPdfValue("P62_OV2_TITLE")+"\n");
                }

                if (GCGModesParser.dicStateModeForPdf.containsKey("P62_OV3_TITLE") && GCGModesParser.dicStateModeForPdf.get("P62_OV3_TITLE") != null) {
                    buider.append("- Because you're P62, you can:  "+getStateModeForPdfValue("P62_OV3_TITLE")+"\n");
                }
            }
        }

    }

    private static void printForMode3(StringBuilder buider) {
        if (GCGModesParser.arrQuestionaireSelected.contains("Option1")) {

            if (GCGModesParser.dicStateMode.containsKey("M3_OV1_OPTION1") && GCGModesParser.dicStateMode.get("M3_OV1_OPTION1") != null) {
                buider.append("- Because you're option 1, you can:  "+getStateModeForPdfValue("M3_OV1_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateMode.containsKey("M3_OV2_OPTION1") && GCGModesParser.dicStateMode.get("M3_OV2_OPTION1") != null) {
                buider.append("- Because you're option 1, you can:  "+getStateModeForPdfValue("M3_OV2_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("P43_OV1_TITLE") && GCGModesParser.dicStateModeForPdf.get("P43_OV1_TITLE") != null) {
                buider.append("- Because you're option 1, you can:  "+getStateModeForPdfValue("P43_OV1_TITLE")+"\n");
            }
        }

        if (GCGModesParser.arrQuestionaireSelected.contains("Option2")) {

            if (GCGModesParser.dicStateMode.containsKey("M3_OV1_OPTION2") && GCGModesParser.dicStateMode.get("M3_OV1_OPTION2") != null) {
                buider.append("- Because you're option 2, you can:  "+getStateModeForPdfValue("M3_OV1_TITLE")+"\n");
            }

            if (GCGModesParser.dicStateModeForPdf.containsKey("M3_OV3_TITLE") && GCGModesParser.dicStateModeForPdf.get("M3_OV3_TITLE") != null) {
                buider.append("- Because you're option 2, you can:  "+getStateModeForPdfValue("M3_OV3_TITLE")+"\n");
            }
        }

        if (GCGModesParser.arrQuestionaireSelected.contains("Option3")) {

            if (GCGModesParser.dicStateMode.containsKey("M3_OV1_OPTION3") && GCGModesParser.dicStateMode.get("M3_OV1_OPTION3") != null) {
                buider.append("- Because you're option 3, you can:  "+getStateModeForPdfValue("M3_OV1_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateMode.containsKey("M3_OV2_OPTION3") && GCGModesParser.dicStateMode.get("M3_OV2_OPTION3") != null) {
                buider.append("- Because you're option 3, you can:  "+getStateModeForPdfValue("M3_OV2_TITLE")+"\n");
            }

            if (GCGModesParser.dicStateModeForPdf.containsKey("M3_OC3_TITLE") && GCGModesParser.dicStateModeForPdf.get("M3_OC3_TITLE").equalsIgnoreCase("M3_OC3_OPTIONC")) {

                if (GCGModesParser.dicStateModeForPdf.containsKey("P63_D1_TITLE") && GCGModesParser.dicStateModeForPdf.get("P63_D1_TITLE") != null) {
                    if (GCGModesParser.dicStateModeForPdf.containsKey("P63_OV1_TITLE") && GCGModesParser.dicStateModeForPdf.get("P63_OV1_TITLE") != null) {
                        buider.append("- Because you're P63, you can:  "+getStateModeForPdfValue("P63_OV1_TITLE")+"\n");
                    }
                    if (GCGModesParser.dicStateModeForPdf.containsKey("P63_OV2_TITLE") && GCGModesParser.dicStateModeForPdf.get("P63_OV2_TITLE") != null) {
                        buider.append("- Because you're P63, you can:  "+getStateModeForPdfValue("P63_OV2_TITLE")+"\n");
                    }
                }
            }

            else{
                if (GCGModesParser.dicStateModeForPdf.containsKey("P63_OV1_TITLE") && GCGModesParser.dicStateModeForPdf.get("P63_OV1_TITLE") != null) {
                    buider.append("- Because you're option 3, you can:  "+getStateModeForPdfValue("P63_OV1_TITLE")+"\n");
                }
                if (GCGModesParser.dicStateModeForPdf.containsKey("P63_OV2_TITLE") && GCGModesParser.dicStateModeForPdf.get("P63_OV2_TITLE") != null) {
                    buider.append("- Because you're option 3, you can:  "+getStateModeForPdfValue("P63_OV2_TITLE")+"\n");
                }
            }

        }

        if (GCGModesParser.arrQuestionaireSelected.contains("Option4")) {
            if (GCGModesParser.dicStateModeForPdf.containsKey("M3_OV4_TITLE") && GCGModesParser.dicStateModeForPdf.get("M3_OV4_TITLE") != null) {
                buider.append("- Because you're option 4, you can: "+getStateModeForPdfValue("M3_OV4_TITLE")+"\n");
            }
        }


        if ((GCGModesParser.dicStateModeForPdf.containsKey("M3_D4_TITLE") && GCGModesParser.dicStateModeForPdf.get("M3_D4_TITLE").equalsIgnoreCase("yes"))) {
            if ((GCGModesParser.dicStateModeForPdf.containsKey("M3_OV4_TITLE") && GCGModesParser.dicStateModeForPdf.get("M3_OV4_TITLE")!=null)) {
                buider.append("- Because you have M3_D4, you can:  "+getStateModeForPdfValue("M3_OV4_TITLE")+"\n");
            }
        }


        if ((GCGModesParser.dicStateModeForPdf.containsKey("P53_D1_TITLE") && GCGModesParser.dicStateModeForPdf.get("P53_D1_TITLE") != null)
                ||
                (GCGModesParser.dicStateModeForPdf.containsKey("P53_H11_TITLE") && GCGModesParser.dicStateModeForPdf.get("P53_H11_TITLE") != null)
                ||
                (GCGModesParser.dicStateModeForPdf.containsKey("P53_H10_TITLE") && GCGModesParser.dicStateModeForPdf.get("P53_H10_TITLE") != null)
                ) {
            if (GCGModesParser.dicStateModeForPdf.containsKey("P53_OV1_TITLE") && GCGModesParser.dicStateModeForPdf.get("P53_OV1_TITLE") != null) {
                buider.append("- Because you're P53, you can:  "+getStateModeForPdfValue("P53_OV1_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("P53_OV2_TITLE") && GCGModesParser.dicStateModeForPdf.get("P53_OV2_TITLE") != null) {
                buider.append("- Because you're P53, you can:  "+getStateModeForPdfValue("P53_OV2_TITLE")+"\n");
            }
            if (GCGModesParser.dicStateModeForPdf.containsKey("P53_OV3_TITLE") && GCGModesParser.dicStateModeForPdf.get("P53_OV3_TITLE") != null) {
                buider.append("- Because you're P53, you can:  "+getStateModeForPdfValue("P53_OV3_TITLE")+"\n");
            }

        }

        if (GCGModesParser.dicStateModeForPdf.containsKey("P63_D1_TITLE") && GCGModesParser.dicStateModeForPdf.get("P63_D1_TITLE") != null) {
            if (!GCGModesParser.dicStateModeForPdf.containsKey("M3_OC3_TITLE") || !GCGModesParser.dicStateModeForPdf.get("M3_OC3_TITLE").equalsIgnoreCase(getStateModeForPdfValue("M3_OC3_OPTIONC"))) {
                if (GCGModesParser.dicStateModeForPdf.containsKey("P63_OV1_TITLE") && GCGModesParser.dicStateModeForPdf.get("P63_OV1_TITLE") != null) {
                    buider.append("- Because you're P63, you can:  "+getStateModeForPdfValue("P63_OV1_TITLE")+"\n");
                }
                if (GCGModesParser.dicStateModeForPdf.containsKey("P63_OV2_TITLE") && GCGModesParser.dicStateModeForPdf.get("P63_OV2_TITLE") != null) {
                    buider.append("- Because you're P63, you can:  "+getStateModeForPdfValue("P63_OV2_TITLE")+"\n");
                }


            }
        }


    }


    private static String addSummaryBeforeSteps() {
        StringBuilder builder = new StringBuilder();
        builder.append("To summarize, these are the action steps you could take to obtain permanent residence in the US:\n\n");

        if (GCGModesParser.dicStateModeForPdf.containsKey("D1_TITLE") && GCGModesParser.dicStateModeForPdf.get("D1_TITLE").equalsIgnoreCase("no")) {
            printForMode3(builder);
        }

        if (GCGModesParser.dicStateModeForPdf.containsKey("D4_TITLE") && GCGModesParser.dicStateModeForPdf.get("D4_TITLE").equalsIgnoreCase("yes")) {
            printForMode1(builder);
        }

        if (GCGModesParser.dicStateModeForPdf.containsKey("D2_TITLE") && GCGModesParser.dicStateModeForPdf.get("D2_TITLE") != null) {
            printForMode2(builder);
        }


        builder.append("\n");


        if (GCGModesParser.dicStateModeForPdf.containsKey("AP_OV2_TITLE") && GCGModesParser.dicStateModeForPdf.get("AP_OV2_TITLE") != null
                ||
                GCGModesParser.dicStateModeForPdf.containsKey("AP_OV3_TITLE") && GCGModesParser.dicStateModeForPdf.get("AP_OV3_TITLE") != null
                ||
                GCGModesParser.dicStateModeForPdf.containsKey("AP_OV1_TITLE") && GCGModesParser.dicStateModeForPdf.get("AP_OV1_TITLE") != null
                ||
                GCGModesParser.dicStateModeForPdf.containsKey("AP_OV4_TITLE") && GCGModesParser.dicStateModeForPdf.get("AP_OV4_TITLE") != null
                ||
                GCGModesParser.dicStateModeForPdf.containsKey("RP_OV1_TITLE") && GCGModesParser.dicStateModeForPdf.get("RP_OV1_TITLE") != null

                ) {
            builder.append("These are the action steps you could take to stay temporarily in the US:\n\n");
        }

        if (GCGModesParser.dicStateModeForPdf.containsKey("AP_OV2_TITLE") && GCGModesParser.dicStateModeForPdf.get("AP_OV2_TITLE") != null) {
            builder.append("- "+getStateModeForPdfValue("AP_OV2_TITLE")+"\n");
        }
        if (GCGModesParser.dicStateModeForPdf.containsKey("AP_OV3_TITLE") && GCGModesParser.dicStateModeForPdf.get("AP_OV3_TITLE") != null) {
            builder.append("- "+getStateModeForPdfValue("AP_OV3_TITLE")+"\n");
        }

        if (GCGModesParser.dicStateModeForPdf.containsKey("AP_OV1_TITLE") && GCGModesParser.dicStateModeForPdf.get("AP_OV1_TITLE") != null) {
            builder.append("- "+getStateModeForPdfValue("AP_OV1_TITLE")+"\n");
        }

        if (GCGModesParser.dicStateModeForPdf.containsKey("AP_OV4_TITLE") && GCGModesParser.dicStateModeForPdf.get("AP_OV4_TITLE") != null) {
            builder.append("- "+getStateModeForPdfValue("AP_OV4_TITLE")+"\n");
        }

        if (GCGModesParser.dicStateModeForPdf.containsKey("RP_OV1_TITLE") && GCGModesParser.dicStateModeForPdf.get("RP_OV1_TITLE") != null) {
            builder.append("- "+getStateModeForPdfValue("RP_OV1_TITLE")+"\n");
        }

        if (GCGModesParser.dicStateModeForPdf.containsKey("AP_OV2_TITLE") && GCGModesParser.dicStateModeForPdf.get("AP_OV2_TITLE") != null
                ||
                GCGModesParser.dicStateModeForPdf.containsKey("AP_OV3_TITLE") && GCGModesParser.dicStateModeForPdf.get("AP_OV3_TITLE") != null
                ||
                GCGModesParser.dicStateModeForPdf.containsKey("AP_OV1_TITLE") && GCGModesParser.dicStateModeForPdf.get("AP_OV1_TITLE") != null
                ||
                GCGModesParser.dicStateModeForPdf.containsKey("AP_OV4_TITLE") && GCGModesParser.dicStateModeForPdf.get("AP_OV4_TITLE") != null
                ||
                GCGModesParser.dicStateModeForPdf.containsKey("RP_OV1_TITLE") && GCGModesParser.dicStateModeForPdf.get("RP_OV1_TITLE") != null

                ) {

            builder.append("\n");
        }

        return builder.toString();
    }

    private static String getStateModeForPdfValue(String key){
        String valueFromJson=   GCGModesParser.jsonContantFile.optString(key).equalsIgnoreCase("")
                ?
                key
                : GCGModesParser.jsonContantFile.optString(key);
        return valueFromJson;
    }


}
