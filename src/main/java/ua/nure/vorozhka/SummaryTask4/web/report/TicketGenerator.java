package ua.nure.vorozhka.SummaryTask4.web.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.EdgeStation;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.RouteInfo;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.TrainPlace;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.User;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Stanislav on 19.01.2017.
 */
public final class TicketGenerator {

    private static final Font BIG_FONT = new Font(Font.FontFamily.HELVETICA,
            28, Font.BOLD);

    private static final Font SMALL_FONT = new Font(Font.FontFamily.HELVETICA,
            13, Font.ITALIC | Font.BOLD);

    private static final int TITLE_TOP_MERGING = 20;

    private static final int PARAGRAPH_BOTTOM_MERGING = 5;

    private static final int TOP_MERGING = 10;

    private static final int BOTTOM_MARGIN = 15;

    private TicketGenerator() {

    }

    public static void createTicket(
            User user, RouteInfo routeInfo, TrainPlace trainPlace, String path)
            throws IOException, DocumentException {

        // Document creation
        Document ticket = new Document(PageSize.A5);

        PdfWriter.getInstance(ticket,
                new FileOutputStream(String.format("%s%s.pdf", path, user.getLogin())));

        ticket.open();

        // Title
        Paragraph title = getTitle();
        ticket.add(title);

        // Table of time
        PdfPTable tableOfCommonInfo = getTableOfCommonInfo(user, routeInfo);
        ticket.add(tableOfCommonInfo);

        // Price paragraph
        Paragraph priceInfo = getPriceParagraph();
        ticket.add(priceInfo);

        // Table of price
        PdfPTable tableOfPriceInfo = getTableOfPrice(trainPlace);
        ticket.add(tableOfPriceInfo);

        // Image, logo
        Image stamp = getStampImg(path);
        ticket.add(stamp);

        ticket.close();
    }

    private static Image getStampImg(String path) throws BadElementException, IOException {
        String imageUrl = String.format("%srailway_stamp.png", path);
        Image stamp = Image.getInstance(imageUrl);
        stamp.setAlignment(Element.ALIGN_RIGHT);
        return stamp;
    }

    private static PdfPTable getTableOfPrice(TrainPlace trainPlace) {
        PdfPTable tableOfPriceInfo = new PdfPTable(2);

        tableOfPriceInfo.addCell("Place type:");
        tableOfPriceInfo.addCell(trainPlace.getType().getName());

        tableOfPriceInfo.addCell("Price:");
        tableOfPriceInfo.addCell(String.format("%d $", trainPlace.getCost()));
        return tableOfPriceInfo;
    }

    private static Paragraph getPriceParagraph() {
        Paragraph priceInfo = new Paragraph();
        priceInfo.setFont(SMALL_FONT);
        priceInfo.setSpacingAfter(PARAGRAPH_BOTTOM_MERGING);
        priceInfo.add(new Chunk("Information about the trip price, $."));
        return priceInfo;
    }

    private static PdfPTable getTableOfCommonInfo(User user, RouteInfo routeInfo) {
        PdfPTable tableOfCommonInfo = new PdfPTable(2);
        EdgeStation initialEdgeStation = routeInfo.getInitialStation();
        EdgeStation terminalEdgeStation = routeInfo.getTerminalStation();

        tableOfCommonInfo.setSpacingBefore(TOP_MERGING);
        tableOfCommonInfo.setSpacingAfter(BOTTOM_MARGIN);

        tableOfCommonInfo.addCell("FULL NAME:");
        tableOfCommonInfo.addCell(user.getFullName().toUpperCase());

        tableOfCommonInfo.addCell("Dispatch station:");
        tableOfCommonInfo.addCell(initialEdgeStation.getStationName());

        tableOfCommonInfo.addCell("Arrival station:");
        tableOfCommonInfo.addCell(terminalEdgeStation.getStationName());

        tableOfCommonInfo.addCell("Date and time of departure:");
        tableOfCommonInfo.addCell(String.format("%s %s", initialEdgeStation.getDate().toString(),
                initialEdgeStation.getTime().toString()));

        tableOfCommonInfo.addCell("Date and time of arrival:");
        tableOfCommonInfo.addCell(String.format("%s %s", terminalEdgeStation.getDate().toString(),
                terminalEdgeStation.getTime().toString()));

        tableOfCommonInfo.addCell("Train number:");
        tableOfCommonInfo.addCell(String.valueOf(routeInfo.getTrain().getNumber()));
        return tableOfCommonInfo;
    }

    private static Paragraph getTitle() {
        Paragraph title = new Paragraph("RAILWAY TICKET", BIG_FONT);
        title.setAlignment(Element.ALIGN_LEFT);
        title.setSpacingBefore(TITLE_TOP_MERGING);
        title.setSpacingAfter(BOTTOM_MARGIN);
        return title;
    }
}
