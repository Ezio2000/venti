import org.venti.common.struc.dform.cell.impl.BooleanCell;
import org.venti.common.struc.dform.cell.impl.NumberCell;
import org.venti.common.struc.dform.cell.impl.TextCell;
import org.venti.common.struc.dform.form.Form;
import org.venti.common.struc.dform.sheet.Sheet;
import org.venti.common.struc.dform.visitor.OrderlyVisitor;

import java.util.concurrent.locks.LockSupport;

void main() {
    var form = new Form("Form");
    var sheet1 = new Sheet("Sheet1");
    var sheet2 = new Sheet("Sheet2");
    var cell11 = new TextCell("cell1", "cell1");
    var cell12 = new TextCell("cell2", "cell2");
    var cell21 = new NumberCell("cell3", "3");
    var cell22 = new BooleanCell("cell4", "true");
    var cell23 = new NumberCell("cell5", "你好");
    form.addSheet(sheet1);
    form.addSheet(sheet2);
    sheet1.addCell(cell11);
    sheet1.addCell(cell12);
    sheet2.addCell(cell21);
    sheet2.addCell(cell22);
    sheet2.addCell(cell23);
    form.accept(new OrderlyVisitor());
    LockSupport.park();
}
