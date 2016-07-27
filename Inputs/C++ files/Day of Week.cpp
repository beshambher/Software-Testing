#include<iostream>
using namespace std;
#include<conio.h>
int main()
{
    int n;
    cout<<"Enter number of week's day(1-7): ";
    cin>>n;
    cout<<endl;
	if((n>0)&&(n<8)) {   
		cout<<"The day is: "; 
	    switch(n)
	    {
			case 1: cout<<" Sunday"<<endl;
					 break;
			case 2: cout<<" Monday"<<endl;
					 break;
			case 3: cout<<" Tuesday"<<endl;
					 break;
			case 4: cout<<" Wednesday"<<endl;
					 break;
			case 5: cout<<" Thursday"<<endl;
					 break;
			case 6: cout<<" Friday"<<endl;
					 break;
			case 7: cout<<" Saturday"<<endl;
					 break;
			default: cout<<" Invalid"<<endl;
	    }  
	}
	else {
		cout<<"Inputs out of range."<<endl;
	}  
    return 0;
}
