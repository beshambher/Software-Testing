#include<iostream>
using namespace std;
int main()
{
	int a, b, c;
	cout<<"Enter three numbers (between 1-100): ";
	cin>>a>>b>>c;
	if((a>0)&&(a<101)&&(b>0)&&(b<101)&&(c>0)&&(c<101)) {
	  	if(a<b) {
			if(a<c) {
				cout<<"The smallest number is "<<a<<endl;
			}
			else {
				cout<<"The Smallest number is "<<c<<endl;
			}
		}
		else {
		   if(b<c) {
		      cout<<"The smallest number is "<<b<<endl;
		   }
			else {
				cout<<"The Smallest number is "<<c<<endl;
			}
		}
	}
	else {
		cout<<"Inputs out of range."<<endl;
	}
	return 0;
}
