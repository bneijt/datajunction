Datajunction
============

Small back-up system. The idea is to have various input sources (like directories) map onto different storage locations (like web servers, DHTs, and other directories).

The first obtainable goal will be to have a commandline utility which can be used to back up files into a chunk based directory structure on a separate disk. This will be the ChunkStorage, which will store metadata in a json document and split the attachment over multiple sha1sum named files.

Storage systems
===============

ChunkStorage
------------
CHUNK_SIZE = 1024 * 1024 bytes

root/data/[first 2 characters of chunk sha1]/[full sha1 sum]
root/meta/[first 2 characters of full sha1]/[full sha1 sum].json

The json will contain the metadata and all hash references.

In the future it should be possible to replicate data from various locations to various other locations, creating a similair behaviour as [Unison](http://www.cis.upenn.edu/~bcpierce/unison/) does but with vastly different storage systems behind each location.

Development plan
----------------
Current focus: easy backup, care-free backup. Open format, not tar (need support for ACL etc.)
- Build a small CLI to do back-ups into chunks in a directory
- Add watch/trigger functionality? (when disk is inserted, backup)
- Add remote storage capabilities
- Add storage access capabilities
- Create a GUI

....


