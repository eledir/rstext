package edu.southampton.wais.STPLibrary.model;


public class TrigramModel {



		private AnnotatedWord string1;	
		private AnnotatedWord string2;
		private AnnotatedWord string3;

		


		public TrigramModel(AnnotatedWord string1,AnnotatedWord string2, AnnotatedWord string3) {
		    this.string1 = string1;
			this.string2 = string2;
			this.string3=string3;
		}
		public AnnotatedWord getString1() {
			return string1;
		}
		public void setString1(AnnotatedWord string1) {
			this.string1 = string1;
		}
		public AnnotatedWord getString2() {
			return string2;
		}
		public void setString2(AnnotatedWord string2) {
			this.string2 = string2;
		}



		
		public AnnotatedWord getString3() {
			return string3;
		}
		public void setString3(AnnotatedWord string3) {
			this.string3 = string3;
		}
		
		
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((string1 == null) ? 0 : string1.hashCode());
			result = prime * result
					+ ((string2 == null) ? 0 : string2.hashCode());
			result = prime * result
					+ ((string3 == null) ? 0 : string3.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TrigramModel other = (TrigramModel) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (string1 == null) {
				if (other.string1 != null)
					return false;
			} else if (!string1.equals(other.string1))
				return false;
			if (string2 == null) {
				if (other.string2 != null)
					return false;
			} else if (!string2.equals(other.string2))
				return false;
			if (string3 == null) {
				if (other.string3 != null)
					return false;
			} else if (!string3.equals(other.string3))
				return false;
			return true;
		}
		private TrigramModel getOuterType() {
			return TrigramModel.this;
		}
		@Override
		public String toString() {
			return "(" + string1 + "," + string2
					+ "," + string3 + ")";
		}
		








		}
