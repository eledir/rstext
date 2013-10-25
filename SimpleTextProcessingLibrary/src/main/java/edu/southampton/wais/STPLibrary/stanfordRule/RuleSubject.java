package edu.southampton.wais.STPLibrary.stanfordRule;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.collect.Table;

import edu.southampton.wais.STPLibrary.model.SentenceModel;
import edu.southampton.wais.STPLibrary.nlp.POSTagStanford;

public class RuleSubject implements Iterable<String> {

	private SentenceModel sm;

	private List<String> subj;

	private enum TagXSubj {
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

	private enum TagSubjX {

		RCMOD {
			public String toString() {
				return "rcmod";
			}
		}

	};

	public RuleSubject(SentenceModel sm) {

		subj = Lists.newArrayList();

		this.sm = sm;

	}

	public void run() {

		Table<String, String, String> tableGov = this.sm.getTableDepGovern();

		for (TagXSubj dep : TagXSubj.values()) {

			Collection<String> s = tableGov.row(dep.toString()).values();

			for (String item : s) {

				String[] itemSplit = item.split("-");

				try {

					if (POSTagStanford.isNoun(itemSplit[1])
							&& !this.subj.contains(item)) {

						this.subj.add(item);

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

		Table<String, String, String> tableDep = this.sm.getTableDepDep();

		for (TagSubjX dep : TagSubjX.values()) {

			Collection<String> s = tableDep.row(dep.toString()).values();

				for (String item : s) {

					String[] itemSplit = item.split("-");

					try {

						if (POSTagStanford.isNoun(itemSplit[1])
								&& !this.subj.contains(item)) {

							this.subj.add(item);

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
		return this.subj.iterator();
	}

	public List<String> getSubj() {
		return subj;
	}

}
