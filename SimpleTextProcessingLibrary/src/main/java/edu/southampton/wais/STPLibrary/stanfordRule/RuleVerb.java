package edu.southampton.wais.STPLibrary.stanfordRule;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.collect.Table;

import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;

public class RuleVerb implements Iterable<String> {

	private SentenceModel sm;

	private List<String> verb;

	private enum TagVerbX {

		DOBJ {
			public String toString() {
				return "dobj";
			}
		},

		IOBJ {
			public String toString() {
				return "iobj";
			}
		},

	
		AGENT {
			public String toString() {
				return "agent";
			}
		},

		NSUBJ {
			public String toString() {
				return "nsubj";
			}
		},

		NSUBJPASS {
			public String toString() {
				return "nsubjpass";
			}
		}
		
		
	
	
	
	};
	
	
	

	
	
	
	

	public RuleVerb(SentenceModel sm) {

		verb = Lists.newArrayList();
		this.sm = sm;
	}

	public void run() {

		Table<String, String, String> tableDepe = this.sm.getTableDepDep();

		for (TagVerbX dep : TagVerbX.values()) {

			Collection<String> s = tableDepe.row(dep.toString()).values();

			for (String item : s) {

				String[] itemSplit = item.split("-");

				try {

					if (POSTagStanford.isVerb(itemSplit[1])
							&& !this.verb.contains(item)) {

						this.verb.add(item);

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}

	@Override
	public Iterator<String> iterator() {
		// TODO Auto-generated method stub
		return this.verb.iterator();
	}

	public List<String> getVerb() {
		return verb;
	}

}
