#include <QCoreApplication>

#include <iostream>

using std::cout;
using std::endl;

int main(int argc, char *argv[]) {
    QCoreApplication a(argc, argv);
    cout << "Hi there!" << endl;
    return a.exec();
}
