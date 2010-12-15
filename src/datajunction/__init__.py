import pkg_resources

__version__ = pkg_resources.require("datajunction")[0].version
__import__('pkg_resources').declare_namespace(__name__)

import datajunction

