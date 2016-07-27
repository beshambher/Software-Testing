#include<iostream>
using namespace std;
int main()
{
	int a, b, c;
	cout<<"Enter the three numbers (between 1-100): ";
	cin>>a>>b>>c;
  	if((a>0)&&(a<101)&&(b>0)&&(b<101)&&(c>0)&&(c<101)) {
	  	if(a==b) {
			if(a==c) {
				cout<<a<<" and "<<b<<" and "<<c<<" are equal"<<endl;
			}
			else {
			   cout<<a<<" and "<<b<<" are equal"<<endl;
			}	
		}
		else {   
		   if(b==c) {
		      cout<<b<<" and "<<c<<" are equal"<<endl;
		   }
		   else {
			  if(a==c) {
			  	cout<<a<<" and "<<c<<" are equal"<<endl;
			  }
			  else {
			  	cout<<a<<" and "<<b<<" and "<<c<<" are not equal"<<endl;
			  }
		   }
		}
	}
	else {
		cout<<"Inputs out of range."<<endl;
	}	
	return 0;
}
