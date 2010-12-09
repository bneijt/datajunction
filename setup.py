#!/usr/bin/python

from distutils.core import setup
execfile('src/datajunction/__init__.py')

setup(
    name='datajunction',
    version=__version__,
    description='Backup utility using hash based chunks and json metadata',
    author='A. Bram Neijt',
    author_email='bram@neijt.nl',
    url='http://www.logfish.net/pr/datajunction',
    packages=['datajunction'],
    package_dir={'datajunction': 'src/datajunction'},
    scripts=['src/dataj' ],
    license = __license__,
    )

