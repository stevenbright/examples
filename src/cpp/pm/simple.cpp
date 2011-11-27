#include <iostream>
#include <cstring>

#include "pattern_matching.h"
#include "match_receivers.h"

using namespace std;
using namespace PatternMatching;

int main() {
 {
    LogMatchReceiver matchReceiver;
    cout << "trying to do pm" << endl;
    
    do_match("asudfka", "as?d*", matchReceiver);    
    cout << matchReceiver.report() << endl;
 }
    cout << "another" << endl;

 {
    LogMatchReceiver matchReceiver;
    do_match("abcdefjh", "ab*jh", matchReceiver);
    cout << matchReceiver.report() << endl;
 }
    return 0;
}

