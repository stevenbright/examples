from __future__ import with_statement
from fabric.api import local, settings, abort, run, cd
from fabric.contrib.console import confirm

def deploy():
  print('Creating /tmp/test')
  run('touch /tmp/test')



