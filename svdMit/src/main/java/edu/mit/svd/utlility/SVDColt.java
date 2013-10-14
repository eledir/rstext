package edu.mit.svd.utlility;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;


public class SVDColt {

	
private DenseDoubleMatrix2D vt;	
private DenseDoubleMatrix2D ut;
private DenseDoubleMatrix1D eng;
private DenseDoubleMatrix2D utengk;
private DenseDoubleMatrix2D vtengk;




public SVDColt() {
	super();
}
public DenseDoubleMatrix2D getVt() {
	return vt;
}
public void setVt(DenseDoubleMatrix2D vt) {
	this.vt = vt;
}
public DenseDoubleMatrix2D getUt() {
	return ut;
}
public void setUt(DenseDoubleMatrix2D ut) {
	this.ut = ut;
}
public  DenseDoubleMatrix1D  getEng() {
	return eng;
}
public void setEng(DenseDoubleMatrix1D eng) {
	this.eng = eng;
}


public DenseDoubleMatrix2D getUtengk() {
	return utengk;
}
public void setUtengk(DenseDoubleMatrix2D utengk2) {
	this.utengk = utengk2;
}
public DenseDoubleMatrix2D getVtengk() {
	return vtengk;
}
public void setVtengk(DenseDoubleMatrix2D vtengk) {
	this.vtengk = vtengk;
}
	




}
