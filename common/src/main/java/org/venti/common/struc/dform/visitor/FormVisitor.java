package org.venti.common.struc.dform.visitor;

import org.venti.common.struc.dform.cell.Cell;
import org.venti.common.struc.dform.form.Form;
import org.venti.common.struc.dform.sheet.Sheet;

public interface FormVisitor {

    void visit(Form form);

}
