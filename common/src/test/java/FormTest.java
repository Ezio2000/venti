import org.venti.common.struc.dform.cell.impl.BooleanCell;
import org.venti.common.struc.dform.cell.impl.DateCell;
import org.venti.common.struc.dform.cell.impl.NumberCell;
import org.venti.common.struc.dform.cell.impl.TextCell;
import org.venti.common.struc.dform.core.Cell;
import org.venti.common.struc.dform.core.Form;
import org.venti.common.struc.dform.core.Sheet;
import org.venti.common.struc.dform.format.Formatter;
import org.venti.common.struc.dform.visitor.AggrVisitor;

import java.util.concurrent.locks.LockSupport;

void main() {
    var form = new Form("Form");
    var sheet1 = new Sheet("Sheet1");
    var sheet2 = new Sheet("Sheet2");
    var cell11 = new TextCell("cell11", "cell1");
    var cell12 = new TextCell("cell12", "cell2");
    var cell21 = new NumberCell("cell21", "3.14%");
    var cell22 = new BooleanCell("cell22", "true");
    var cell23 = new NumberCell("cell23", "你好");
    var cell24 = new DateCell("cell24", "2018-11-23");
    form.addSheet(sheet1);
    form.addSheet(sheet2);
    sheet1.addCell(cell11);
    sheet1.addCell(cell12);
    sheet2.addCell(cell21);
    sheet2.addCell(cell22);
    sheet2.addCell(cell23);
    sheet2.addCell(cell24);
    form.accept(new AggrVisitor() {
//    form.accept(new AsyncAggrVisitor() {
        @Override
        public void visit0(Cell<?> cell) {
            System.out.println(Thread.currentThread());
            try {
                System.out.println(STR."------ \{cell.getName()} : \{cell.format()}");
            } catch (Formatter.FormatException e) {
                System.out.println(STR."------ \{cell.getName()} : 格式错误[\{cell.getType()}][\{cell.getData()}]");
            }
        }
        @Override
        public void visit0(Sheet sheet) {
            System.out.println(STR."--- \{sheet.getName()}");
        }
        @Override
        public void visit0(Form form) {
            System.out.println(Thread.currentThread());
            System.out.println(STR."\{form.getName()}");
        }
    });
    LockSupport.park();
}
