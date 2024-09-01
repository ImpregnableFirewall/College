#include<stdio.h>

int main(){
    int deg1,deg2;
    printf("Enter the degree of the first and second polynomials respectively: ");
    scanf("%d",&deg1);
    scanf("%d",&deg2);
    int i;

    printf("Enter the polynomial 1: \n");
    int pol1[deg1+1];
    for(i=deg1;i>=0;i--){
        printf("Coefficient of x^%d: ",i);
        scanf("%d",&pol1[i]);
    }

    printf("\n Enter the polynomial 2: \n");
    int pol2[deg2+1];
    for(i=deg2;i>=0;i--){
        printf("Coefficient of x^%d: ",i);
        scanf("%d",&pol2[i]);
    }

    int ansdeg=(deg1>deg2)?deg1:deg2;
    int ans[ansdeg];
    if(deg1>deg2){
        for(i=0;i<=deg2;i++){
            ans[i]=pol1[i]+pol2[i];
        }
        for(i=deg2+1;i<=deg1;i++){
            ans[i]=pol1[i];
        }
    }

    if(deg1<=deg2){
        for(i=0;i<=deg1;i++){
            ans[i]=pol1[i]+pol2[i];
        }
        for(i=deg1+1;i<=deg2;i++){
            ans[i]=pol2[i];
        }
    }

    for(i=ansdeg;i>=0;i--){
        if(ans[i]==0){
            continue;
        }
        if(i==0){
            printf("%d",ans[i]);
            continue;
        }
        printf("%d x^%d + ",ans[i],i);
    }
    return 0;
}

