#include<iostream>
using namespace std;
int main()
{
	int a, b;
	cout<<"Enter the two numbers (between 1-100): ";
	cin>>a>>b;
	if((a>0)&&(a<101)&&(b>0)&&(b<101)) {
		if(a>b) {
			cout<<"Greater is "<<a<<endl;
		}
		else if(b>a){
			cout<<"Greater is "<<b<<endl;
		} else {
			cout<<"Both are equal."<<endl;
		}
	}
	else {
		cout<<"Inputs out of range."<<endl;
	}
	return 0;
}
