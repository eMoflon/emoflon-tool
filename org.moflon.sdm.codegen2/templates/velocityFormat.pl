#!/usr/bin/perl
###
#use:
#./velocityFormat.pl <file> to format the file 
#./velocityFormat.pl <dir> to format all files and subdirs recusive
#

use strict;
use warnings;
use File::Copy;
no warnings "recursion";
use 5.6.0;

our @dirs=($ARGV[0]);
our @dirsLeft=($ARGV[0]);

if(-d $ARGV[0]){
	getSubDirs($ARGV[0]);
	foreach my $subdir (@dirs){
		opendir(DIR,$subdir) or die $!." ($subdir)";

		while ( my $vmfile = readdir(DIR)){
			if($vmfile=~/\.vm$/){
				formatFile($vmfile,$subdir);
			}
		}

		closedir(DIR);
	}
} else {
	formatFile($ARGV[0],"");
}

sub getSubDirs {
	if(@dirsLeft){
		my $currDir = shift @dirsLeft; 
		opendir(DIR,$currDir) or die $!;
		while ( my $dfile = readdir(DIR) ) {			
			if(-d $currDir.'/'.$dfile && !($dfile=~/\./)) {
				push(@dirsLeft,$currDir.'/'.$dfile);
				push(@dirs,$currDir.'/'.$dfile);
			}
		}
		closedir DIR;
		getSubDirs();
	}
}

sub formatFile {
	my $file = shift;
	my $dir = shift;
	move($dir.'/'.$file,$dir.'/'.$file.'.old');

	open (IN, '<', $dir.'/'.$file.'.old')  or die $!;
	$file =~ s/.old//;
	open (OUT, '>', $dir.'/'.$file) or die $!;

	my $i = 0;
	my $multilineCommit = 0;
	while ( my $line = <IN>) {
		if ($line=~/#\*/ ) { $multilineCommit = 1 }; #turn the format feature off
		if( $multilineCommit == 0 ){
			if ($line=~/#end/ || $line=~/#else/ ) { $i--; }
			print (OUT "#* ", "   " x $i,  "*#", $line); 
			if ($line=~/#if/ || $line=~/#else/ || $line=~/#foreach/) { $i++; }
		} else {
			print OUT $line; 
		}
		if ($line=~/\*#/ && $multilineCommit == 1 ) { $multilineCommit = 0 }; #turn the format feature on
	}
	close IN;
	close OUT;
}

