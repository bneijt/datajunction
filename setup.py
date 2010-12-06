#!/usr/bin/python

from distutils.core import setup

setup(
    name='Datajunction',
    version='0.1',
    description='Backup utility using hash based chunks and json metadata',
    author='A. Bram Neijt',
    author_email='bram@neijt.nl',
    url='http://www.logfish.net/pr/datajunction',
    #packages=['', 'distutils.command'],
    scripts=['scripts/datajunction_write' ],
    license = 'GPLv3+'
    )

