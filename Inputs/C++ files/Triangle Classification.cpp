#include<iostream>
using namespace std;
int main()
{
	int a;
	int b;
	int c;
	cout<<"Enter the sides of the triangle (between 1-100): ";
	cin>>a>>b>>c;
    cout<<endl;
	if((a>0)&&(a<101)&&(b>0)&&(b<101)&&(c>0)&&(c<101))
	{
		if((a+b)>c) {
			if((a+c)>b) {
				if((b+c)>a) {
					if((a==b)&&(b==c)) {
						cout<<"It is an Equilateral triangle."<<endl;
					}
					else if((a==b)||(b==c)||(a==c)) {
				       cout<<"It is an Isosceles triangle."<<endl;
					}
					else {
					   cout<<"It is an Scalene triangle."<<endl;
					}				
				}
				else {
					cout<<"Invalid triangle!"<<endl;
				}
			}
			else {
					cout<<"Invalid triangle!"<<endl;
			}
		} 
		else {
					cout<<"Invalid triangle!"<<endl;
		}
	}
	else {
		    cout<<"Inputs out of range."<<endl;
	}
	return 0;
}
