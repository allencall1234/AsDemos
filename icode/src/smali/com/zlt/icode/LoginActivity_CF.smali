.class public Lcom/zlt/icode/LoginActivity_CF;
.super Lcom/zlt/icode/BaseActivity;
.source "LoginActivity.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# instance fields
.field private accountLayout:Landroid/widget/LinearLayout;

.field private code:Ljava/lang/String;

.field private mDialog:Landroid/app/Dialog;

.field private mMenuItem:Landroid/view/MenuItem;

.field private mainText:Landroid/widget/TextView;

.field private noAccountLayout:Landroid/widget/LinearLayout;

.field private passEditText:Landroid/widget/EditText;

.field private remenberBox:Landroid/widget/CheckBox;

.field private userId:Ljava/lang/String;


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 24
    invoke-direct {p0}, Lcom/zlt/icode/BaseActivity;-><init>()V

    return-void
.end method

.method private showCustomDialog()V
    .locals 4

    .prologue
    const/4 v3, 0x0

    .line 152
    new-instance v1, Landroid/app/Dialog;

    const v2, 0x7f0900cf

    invoke-direct {v1, p0, v2}, Landroid/app/Dialog;-><init>(Landroid/content/Context;I)V

    iput-object v1, p0, Lcom/zlt/icode/LoginActivity_CF;->mDialog:Landroid/app/Dialog;

    .line 153
    iget-object v1, p0, Lcom/zlt/icode/LoginActivity_CF;->mDialog:Landroid/app/Dialog;

    invoke-virtual {v1, v3}, Landroid/app/Dialog;->setCanceledOnTouchOutside(Z)V

    .line 154
    iget-object v1, p0, Lcom/zlt/icode/LoginActivity_CF;->mDialog:Landroid/app/Dialog;

    invoke-virtual {v1, v3}, Landroid/app/Dialog;->setCancelable(Z)V

    .line 156
    invoke-static {p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v1

    const v2, 0x7f04001e

    const/4 v3, 0x0

    invoke-virtual {v1, v2, v3}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v0

    .line 158
    .local v0, "dialogView":Landroid/view/View;
    const v1, 0x7f0c0069

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    invoke-virtual {v1, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 159
    const v1, 0x7f0c006a

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    invoke-virtual {v1, p0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 161
    const v1, 0x7f0c0067

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/EditText;

    iput-object v1, p0, Lcom/zlt/icode/LoginActivity_CF;->passEditText:Landroid/widget/EditText;

    .line 162
    const v1, 0x7f0c0068

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/CheckBox;

    iput-object v1, p0, Lcom/zlt/icode/LoginActivity_CF;->remenberBox:Landroid/widget/CheckBox;

    .line 163
    iget-object v1, p0, Lcom/zlt/icode/LoginActivity_CF;->remenberBox:Landroid/widget/CheckBox;

    const/16 v2, 0x8

    invoke-virtual {v1, v2}, Landroid/widget/CheckBox;->setVisibility(I)V

    .line 164
    iget-object v1, p0, Lcom/zlt/icode/LoginActivity_CF;->mDialog:Landroid/app/Dialog;

    invoke-virtual {v1, v0}, Landroid/app/Dialog;->setContentView(Landroid/view/View;)V

    .line 165
    iget-object v1, p0, Lcom/zlt/icode/LoginActivity_CF;->mDialog:Landroid/app/Dialog;

    invoke-virtual {v1}, Landroid/app/Dialog;->show()V

    .line 166
    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 6
    .param p1, "view"    # Landroid/view/View;
    .annotation runtime Lcom/alipay/euler/andfix/annotation/MethodReplace;
        method = "onClick"
        clazz = "com.zlt.icode.LoginActivity"
    .end annotation

    .prologue
    .line 70
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v3

    packed-switch v3, :pswitch_data_0

    .line 100
    :pswitch_0
    invoke-virtual {p0}, Lcom/zlt/icode/LoginActivity_CF;->finish()V

    .line 103
    :goto_0
    return-void

    .line 73
    :pswitch_1
    invoke-virtual {p0}, Lcom/zlt/icode/LoginActivity_CF;->finish()V

    goto :goto_0

    .line 76
    :pswitch_2
    new-instance v3, Landroid/content/Intent;

    const-class v4, Lcom/zlt/icode/SearchActivity;

    invoke-direct {v3, p0, v4}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    invoke-virtual {p0, v3}, Lcom/zlt/icode/LoginActivity_CF;->startActivity(Landroid/content/Intent;)V

    goto :goto_0

    .line 79
    :pswitch_3
    new-instance v0, Landroid/content/Intent;

    const-class v3, Lcom/zlt/icode/DetailActivity;

    invoke-direct {v0, p0, v3}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    .line 80
    .local v0, "intent":Landroid/content/Intent;
    const-string v3, "type"

    const/4 v4, 0x0

    invoke-virtual {v0, v3, v4}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    .line 81
    invoke-virtual {p0, v0}, Lcom/zlt/icode/LoginActivity_CF;->startActivity(Landroid/content/Intent;)V

    goto :goto_0

    .line 84
    .end local v0    # "intent":Landroid/content/Intent;
    :pswitch_4
    iget-object v3, p0, Lcom/zlt/icode/LoginActivity_CF;->mDialog:Landroid/app/Dialog;

    invoke-virtual {v3}, Landroid/app/Dialog;->dismiss()V

    goto :goto_0

    .line 88
    :pswitch_5
    iget-object v3, p0, Lcom/zlt/icode/LoginActivity_CF;->sharedPreferences:Landroid/content/SharedPreferences;

    const-string v4, "password"

    const/4 v5, 0x0

    invoke-interface {v3, v4, v5}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    .line 90
    .local v2, "password":Ljava/lang/String;
    iget-object v3, p0, Lcom/zlt/icode/LoginActivity_CF;->passEditText:Landroid/widget/EditText;

    invoke-virtual {v3}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v3, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_0

    .line 91
    iget-object v3, p0, Lcom/zlt/icode/LoginActivity_CF;->mDialog:Landroid/app/Dialog;

    invoke-virtual {v3}, Landroid/app/Dialog;->dismiss()V

    .line 92
    new-instance v1, Landroid/content/Intent;

    const-class v3, Lcom/zlt/icode/AccountActivity;

    invoke-direct {v1, p0, v3}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    .line 93
    .local v1, "intent2":Landroid/content/Intent;
    const-string v3, "changeUserInfo"

    const/4 v4, 0x1

    invoke-virtual {v1, v3, v4}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Z)Landroid/content/Intent;

    .line 94
    invoke-virtual {p0, v1}, Lcom/zlt/icode/LoginActivity_CF;->startActivity(Landroid/content/Intent;)V

    goto :goto_0

    .line 96
    .end local v1    # "intent2":Landroid/content/Intent;
    :cond_0
    const-string v3, "\u5bc6\u7801\u9519\u8bef"

    invoke-virtual {p0, v3}, Lcom/zlt/icode/LoginActivity_CF;->showToast(Ljava/lang/CharSequence;)V

    goto :goto_0

    .line 70
    :pswitch_data_0
    .packed-switch 0x7f0c0069
        :pswitch_4
        :pswitch_5
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_1
        :pswitch_0
        :pswitch_2
        :pswitch_3
    .end packed-switch
.end method

.method protected onCreate(Landroid/os/Bundle;)V
    .locals 1
    .param p1, "savedInstanceState"    # Landroid/os/Bundle;

    .prologue
    .line 41
    invoke-super {p0, p1}, Lcom/zlt/icode/BaseActivity;->onCreate(Landroid/os/Bundle;)V

    .line 42
    const v0, 0x7f040020

    invoke-virtual {p0, v0}, Lcom/zlt/icode/LoginActivity_CF;->setContentView(I)V

    .line 44
    const v0, 0x7f0c006d

    invoke-virtual {p0, v0}, Lcom/zlt/icode/LoginActivity_CF;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/zlt/icode/LoginActivity_CF;->mainText:Landroid/widget/TextView;

    .line 45
    const v0, 0x7f0c0070

    invoke-virtual {p0, v0}, Lcom/zlt/icode/LoginActivity_CF;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    iput-object v0, p0, Lcom/zlt/icode/LoginActivity_CF;->accountLayout:Landroid/widget/LinearLayout;

    .line 46
    const v0, 0x7f0c006e

    invoke-virtual {p0, v0}, Lcom/zlt/icode/LoginActivity_CF;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    iput-object v0, p0, Lcom/zlt/icode/LoginActivity_CF;->noAccountLayout:Landroid/widget/LinearLayout;

    .line 47
    return-void
.end method

.method public onCreateOptionsMenu(Landroid/view/Menu;)Z
    .locals 9
    .param p1, "menu"    # Landroid/view/Menu;

    .prologue
    const/4 v8, 0x1

    const/4 v4, 0x0

    .line 108
    const-string v2, "zlt"

    const-string v3, "Login OnCreateOptionsMenu"

    invoke-static {v2, v3}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 110
    invoke-virtual {p0}, Lcom/zlt/icode/LoginActivity_CF;->getMenuInflater()Landroid/view/MenuInflater;

    move-result-object v2

    const/high16 v3, 0x7f0e0000

    invoke-virtual {v2, v3, p1}, Landroid/view/MenuInflater;->inflate(ILandroid/view/Menu;)V

    .line 111
    invoke-interface {p1, v4}, Landroid/view/Menu;->getItem(I)Landroid/view/MenuItem;

    move-result-object v2

    iput-object v2, p0, Lcom/zlt/icode/LoginActivity_CF;->mMenuItem:Landroid/view/MenuItem;

    .line 112
    iget-object v2, p0, Lcom/zlt/icode/LoginActivity_CF;->userId:Ljava/lang/String;

    if-nez v2, :cond_0

    .line 113
    iget-object v2, p0, Lcom/zlt/icode/LoginActivity_CF;->mMenuItem:Landroid/view/MenuItem;

    invoke-interface {v2, v4}, Landroid/view/MenuItem;->setVisible(Z)Landroid/view/MenuItem;

    .line 116
    :cond_0
    const/4 v1, 0x0

    .line 118
    .local v1, "versionStr":Ljava/lang/String;
    :try_start_0
    const-string v2, "\u7248\u672c\u66f4\u65b0(\u5f53\u524d%1$s)"

    const/4 v3, 0x1

    new-array v3, v3, [Ljava/lang/Object;

    const/4 v4, 0x0

    invoke-virtual {p0}, Lcom/zlt/icode/LoginActivity_CF;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v5

    invoke-virtual {p0}, Lcom/zlt/icode/LoginActivity_CF;->getPackageName()Ljava/lang/String;

    move-result-object v6

    const/4 v7, 0x0

    invoke-virtual {v5, v6, v7}, Landroid/content/pm/PackageManager;->getPackageInfo(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;

    move-result-object v5

    iget-object v5, v5, Landroid/content/pm/PackageInfo;->versionName:Ljava/lang/String;

    aput-object v5, v3, v4

    invoke-static {v2, v3}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    :try_end_0
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    move-result-object v1

    .line 123
    :goto_0
    invoke-interface {p1, v8}, Landroid/view/Menu;->getItem(I)Landroid/view/MenuItem;

    move-result-object v2

    invoke-interface {v2, v1}, Landroid/view/MenuItem;->setTitle(Ljava/lang/CharSequence;)Landroid/view/MenuItem;

    .line 124
    return v8

    .line 119
    :catch_0
    move-exception v0

    .line 121
    .local v0, "e":Landroid/content/pm/PackageManager$NameNotFoundException;
    invoke-virtual {v0}, Landroid/content/pm/PackageManager$NameNotFoundException;->printStackTrace()V

    goto :goto_0
.end method

.method public onOptionsItemSelected(Landroid/view/MenuItem;)Z
    .locals 4
    .param p1, "item"    # Landroid/view/MenuItem;

    .prologue
    const/4 v1, 0x1

    .line 132
    invoke-interface {p1}, Landroid/view/MenuItem;->getItemId()I

    move-result v0

    .line 133
    .local v0, "id":I
    const v2, 0x7f0c0098

    if-ne v0, v2, :cond_0

    .line 134
    invoke-direct {p0}, Lcom/zlt/icode/LoginActivity_CF;->showCustomDialog()V

    .line 147
    :goto_0
    return v1

    .line 136
    :cond_0
    const v2, 0x7f0c0099

    if-ne v0, v2, :cond_1

    .line 137
    invoke-static {p0}, Lcom/zlt/update/UpdateManager;->getInstance(Landroid/content/Context;)Lcom/zlt/update/UpdateManager;

    move-result-object v2

    new-instance v3, Lcom/zlt/icode/LoginActivity$1;

    invoke-direct {v3, p0}, Lcom/zlt/icode/LoginActivity$1;-><init>(Lcom/zlt/icode/LoginActivity;)V

    invoke-virtual {v2, v1, v3}, Lcom/zlt/update/UpdateManager;->checkUpdate(ZLcom/zlt/update/UpdateFailedListener;)V

    goto :goto_0

    .line 147
    :cond_1
    invoke-super {p0, p1}, Lcom/zlt/icode/BaseActivity;->onOptionsItemSelected(Landroid/view/MenuItem;)Z

    move-result v1

    goto :goto_0
.end method

.method protected onResume()V
    .locals 3

    .prologue
    .line 52
    invoke-super {p0}, Lcom/zlt/icode/BaseActivity;->onResume()V

    .line 53
    const-string v0, "zlt"

    const-string v1, "Login OnResume"

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 55
    iget-object v0, p0, Lcom/zlt/icode/LoginActivity_CF;->sharedPreferences:Landroid/content/SharedPreferences;

    const-string v1, "userId"

    const/4 v2, 0x0

    invoke-interface {v0, v1, v2}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/zlt/icode/LoginActivity_CF;->userId:Ljava/lang/String;

    .line 57
    iget-object v0, p0, Lcom/zlt/icode/LoginActivity_CF;->userId:Ljava/lang/String;

    if-eqz v0, :cond_0

    .line 58
    iget-object v0, p0, Lcom/zlt/icode/LoginActivity_CF;->accountLayout:Landroid/widget/LinearLayout;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setVisibility(I)V

    .line 59
    iget-object v0, p0, Lcom/zlt/icode/LoginActivity_CF;->noAccountLayout:Landroid/widget/LinearLayout;

    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/widget/LinearLayout;->setVisibility(I)V

    .line 60
    iget-object v0, p0, Lcom/zlt/icode/LoginActivity_CF;->mainText:Landroid/widget/TextView;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "Deal "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-object v2, p0, Lcom/zlt/icode/LoginActivity_CF;->userId:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, ",Welcome to back!"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 61
    iget-object v0, p0, Lcom/zlt/icode/LoginActivity_CF;->mMenuItem:Landroid/view/MenuItem;

    if-eqz v0, :cond_0

    .line 62
    iget-object v0, p0, Lcom/zlt/icode/LoginActivity_CF;->mMenuItem:Landroid/view/MenuItem;

    const/4 v1, 0x1

    invoke-interface {v0, v1}, Landroid/view/MenuItem;->setVisible(Z)Landroid/view/MenuItem;

    .line 67
    :cond_0
    return-void
.end method
