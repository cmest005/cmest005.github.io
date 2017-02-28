	#!/bin/bash
	
	LISTLEN=201
	TESTFILE=/test/file1
	TESTFILE2=/test/file2
	
	for i in `seq 1 $LISTLEN`
	do
	echo "String 1 x$i" >> $TESTFILE
	echo "String 2 x$i" >> $TESTFILE2
	echo "String 3 x$i" >> $TESTFILE
	done
	
	TESTSIZE=$(stat -c%s "$TESTFILE")
	TESTSIZE2=$(stat -c%s "$TESTFILE2")
	
	echo "Added $LISTLEN x 2 lines to first file, $TESTFILE"
	echo "This first test file  is now $TESTSIZE bytes."
	
	echo "Added $LISTLEN lines to the second file, $TESTFILE2"
	echo "This second test file is now $TESTSIZE2 bytes."
