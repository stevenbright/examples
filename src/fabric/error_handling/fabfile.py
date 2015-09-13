from __future__ import with_statement
from fabric.api import local, settings, abort
from fabric.contrib.console import confirm

def test():
  with settings(warn_only = True):
    result = local('sh ./run_tests.sh')
  if result.failed and not confirm('Tests failed. Continue anyway?'):
    abort('Aborting at user request.')
  print('Continue execution...')



