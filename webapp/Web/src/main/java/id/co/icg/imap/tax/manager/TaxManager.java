/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.manager;

import id.co.icg.imap.tax.dao.model.Attachment;
import id.co.icg.imap.tax.dao.model.Attribute;
import id.co.icg.imap.tax.dao.model.TaxPerson;
import id.co.icg.imap.tax.dao.model.Value;
import java.util.List;
import net.sourceforge.stripes.action.FileBean;

/**
 *
 * @author Fauzi Marjalih
 */
public interface TaxManager {
    List<TaxPerson> getListTaxPersons(String npwp);
    List<Attribute> getListAttributes(Long taxId, String nop, String name, String areaCode);
    List<Attachment> getListAttachments(Long attributeId, String fileName);
    List<Value> getListValues(Long attributeId);
    TaxPerson getTaxPerson(String npwp);
    Attachment getAttachment(Long attributeId);
    Value getValue(Long attributeId, Integer year);
    Attribute getAttribute(String nop);

    Integer insertTaxPerson(TaxPerson taxPerson);
    Integer insertAttribute(Attribute attribute);
    Integer insertAttachment(Long attributeId, FileBean fileBean);
}
