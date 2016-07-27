#include<iostream>
using namespace std;
int main()
{
	int a, b, c;
	cout<<"Enter the three numbers (between 1-100): ";
	cin>>a>>b>>c;
  	if((a>=1)&&(a<=100)&&(b>=1)&&(b<=100)&&(c>=1)&&(c<=100)) {
		if(a>b) {
			if(a>c) {
			   cout<<"Greatest number is "<<a<<endl;
			}
			else {
			   cout<<"Greatest number is "<<c<<endl;
			}	
		}
		else {
		   if(b>c) {
		      cout<<"Greatest number is "<<b<<endl;
		   }
		   else {
			  cout<<"Greatest number is "<<c<<endl;
		   }
		}
	}
	else {
		cout<<"Inputs out of range."<<endl;
	}
	return 0;
}
