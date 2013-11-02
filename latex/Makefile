TEX_FILES=$(shell find . -type f -name '*.tex')
BIB_FILES=$(shell find . -type f -name '*.bib')
BBL_FILES=$(shell find . -type f -name '*.bbl')
EPS_FILES=$(shell find . -type f -name '*.eps')
PDF_FILES=$(shell find . -type f -name '*.pdf')

report.pdf : $(TEX_FILES) $(BIB_FILES) $(BBL_FILES) $(EPS_FILES) $(PDF_FILES)
	pdflatex -shell-escape report
	bibtex report
	pdflatex report
	pdflatex report
	./texcount.pl -total -inc main.tex
