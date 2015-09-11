package com.alexshabanov.sample.brikar.website.service;

/**
 * Increments number of active visitors, called from the main page.
 */
public interface VisitorService {
  void incVisitor(String page);
}
