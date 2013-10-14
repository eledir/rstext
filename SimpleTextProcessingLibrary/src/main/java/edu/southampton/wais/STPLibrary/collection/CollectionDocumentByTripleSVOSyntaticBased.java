package edu.southampton.wais.STPLibrary.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Objects;

import edu.southampton.wais.STPLibrary.model.DocumentModelByTrigram;
import edu.southampton.wais.STPLibrary.model.DocumentModelByTriplesSVOSyntacticBased;

public class CollectionDocumentByTripleSVOSyntaticBased implements
Iterable<DocumentModelByTriplesSVOSyntacticBased> {

private List<DocumentModelByTriplesSVOSyntacticBased> list;  


private String name;







public CollectionDocumentByTripleSVOSyntaticBased(String name) {
	

this.list= new ArrayList<DocumentModelByTriplesSVOSyntacticBased>();

this.name=name;
}



public void addDocumentModel(DocumentModelByTriplesSVOSyntacticBased item){
this.list.add(item);
}





@Override
public Iterator<DocumentModelByTriplesSVOSyntacticBased> iterator() {
// TODO Auto-generated method stub
return this.list.iterator();
}



@Override
public String toString() {
return Objects.toStringHelper(this.getClass()).add("Coll ", this.list).toString();
}





public int size(){

return this.list.size();
}



public String getName() {
return name;
}



public void setName(String name) {
this.name = name;
}
}