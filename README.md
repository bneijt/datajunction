Datajunction
------------

Small back-up system. The idea is to have various input sources (like directories) map onto different storage locations (like web servers, DHTs, and other directories).

Storage systems
===============

ChunkStorage
------------
Chunk files into 10 MB pieces, place every file in a name based on the has of the chunk.

root/data/[first 2 characters of chunk sha1]/[rest of sha1]
root/meta/[first 2 characters of full sha1]/[rest of sha1].json

Chunks are 10 MB file parts. The last part will be appended with zeros for hashing (zeros will not be stored on disk).
The json will contain the information on all hashes.

Development plan
----------------
- Build a small CLI to do back-ups into chunks in a directory
- Build/find a metadata/data separation system that is flexible enough to keep track of everything I want.
- Add watch/trigger functionality (when disk is inserted, backup)
- Add remote storage capabilities
- Add storage access capabilities
- Create a GUI



