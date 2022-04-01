package ru.clevertec.tasks.olga.util.printer.impl;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.TextRenderer;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import ru.clevertec.tasks.olga.exception.WritingException;
import ru.clevertec.tasks.olga.util.printer.AbstractPrinter;
import com.itextpdf.layout.Document;
import ru.clevertec.tasks.olga.util.localization.MessageLocaleService;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import static ru.clevertec.tasks.olga.util.Constant.FILENAME_PDF_FORMAT;

@NoArgsConstructor
public class PdfPrinter extends AbstractPrinter {

    @Setter
    private String printPath = ResourceBundle.getBundle("db").getString("path.default");
    private static final String BACKGROUND_PDF = ResourceBundle.getBundle("db").getString("bill.template");
    private final char delimiter = MessageLocaleService
            .getMessage("label.pseudographics_delimiter").charAt(0);
    private final char lineDelimiter = MessageLocaleService
            .getMessage("label.pseudographics_char").charAt(0);

    public PdfPrinter(String printPath) {
        this.printPath = printPath;
    }

    @Override
    public String print(List<String> content) {
        try {
            PdfFont font = PdfFontFactory.createFont(
                    ResourceBundle.getBundle("db").getString("bill.font"),
                    ResourceBundle.getBundle("db").getString("bill.encoding")
            );

            PdfDocument backPdfDocument = new PdfDocument(new PdfReader(BACKGROUND_PDF));

            String fileName = String.format(FILENAME_PDF_FORMAT,
                    LocalDate.now(), System.nanoTime());
            Path path = FileSystems.getDefault().getPath(printPath, fileName);
            Files.createDirectories(path.getParent());

            PdfDocument receiptPdfDocument = new PdfDocument(new PdfWriter(printPath + fileName));
            receiptPdfDocument.addNewPage();

            Document document = new Document(receiptPdfDocument);
            document.setFont(font);
            document.setLeftMargin(100);
            document.setTopMargin(200);
            Table receiptTable = new Table(new float[]{1f})
                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setWidth(UnitValue.createPercentValue(100))
                    .setFontSize(14f);
            for (String line : content) {
                Cell c = new Cell().setBorder(Border.NO_BORDER);
                if (line.charAt(0) == delimiter) {
                    c.add(new Paragraph(printMonocharLine(delimiter, MAX_SYMBOLS_PER_LINE)));
                    receiptTable.addCell(c);
                } else if (line.contains("%s")) {
                    int literals = StringUtils.countMatches(line, "%s");
                    char[] varargs = new char[literals];
                    for (int i = 0; i < literals; i++) {
                        varargs[i] = delimiter;
                    }
                    c.add(new Paragraph(replaceFormatLiteral(line, varargs)));
                    receiptTable.addCell(c);
                } else if (line.charAt(0) == lineDelimiter) {
                    c.add(new Paragraph(printMonocharLine(lineDelimiter, MAX_SYMBOLS_PER_LINE)));
                    receiptTable.addCell(c);
                }
            }
            document.add(receiptTable);

            PdfCanvas canvas = new PdfCanvas(receiptPdfDocument.getFirstPage().newContentStreamBefore(),
                    receiptPdfDocument.getFirstPage().getResources(), receiptPdfDocument);
            PdfFormXObject pdfFormXObject = backPdfDocument.getFirstPage().copyAsFormXObject(receiptPdfDocument);
            canvas.addXObjectAt(pdfFormXObject, 0, 0);

            backPdfDocument.close();
            receiptPdfDocument.close();
            document.close();
            return path.toString();
        } catch (IOException e) {
            throw new WritingException("error.writing");
        }
    }

    private String printMonocharLine(char ch, int len) {
        return StringUtils.repeat(ch, len);
    }

    private String replaceFormatLiteral(String line, char... replacement) {
        int literals = StringUtils.countMatches(line, "%s");
        int len = MAX_SYMBOLS_PER_LINE - line.length() - literals;
        String[] actualReplacement = new String[replacement.length];
        for (int i = 0; i < replacement.length; i++) {
            actualReplacement[i] = StringUtils.repeat(replacement[i], len / replacement.length);
        }
        return String.format(line, actualReplacement);
    }

    private static class CodeRenderer extends TextRenderer {

        public CodeRenderer(Text textElement) {
            super(textElement);
        }

        @Override
        public IRenderer getNextRenderer() {
            return new CodeRenderer((Text) getModelElement());
        }

        @Override
        public void trimFirst() {
        }
    }
}
