import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NavigationBarComponent} from './navigation-bar/navigation-bar.component';
import {FooterBarComponent} from './footer-bar/footer-bar.component';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {HomePageComponent} from './home-page/home-page.component';
import {AngularFireStorageModule} from "@angular/fire/compat/storage";
import {NgxYoutubePlayerModule} from "ngx-youtube-player";
import {CarouselModule} from "ngx-owl-carousel-o";
import {SlickCarouselModule} from "ngx-slick-carousel";
import {NavigationBarSubmenuComponent} from "./navigation-bar/navigation-bar-submenu/navigation-bar-submenu.component";
import {ElementPageComponent} from './element-page/element-page.component';
import {LoginPageComponent} from './login-page/login-page.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RegisterPageComponent} from './register-page/register-page.component';
import {AboutPageComponent} from './about-page/about-page.component';
import {BuyPageComponent} from './buy-page/buy-page.component';
import {MatGridListModule} from "@angular/material/grid-list";
import {MatCardModule} from "@angular/material/card";
import {MatPaginatorModule} from "@angular/material/paginator";
import {ContactPageComponent} from './contact-page/contact-page.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {TokenInterceptorService} from "./services/token-interceptor.service";
import {MatDialogModule} from "@angular/material/dialog";
import {UserProfileComponent} from './user-profile/user-profile.component';
import {DialogErrorComponent} from './dialogerror/dialog-error.component';
import {MatButtonModule} from "@angular/material/button";
import {RentPageComponent} from './rent-page/rent-page.component';
import {AddPropertyComponent} from './add-property/add-property.component';


import {EditPropertyComponent} from './edit-property/edit-property.component';
import {DialogContentComponent} from './dialog-content/dialog-content.component';
import {MatIconModule} from "@angular/material/icon";
import {BookingComponent} from './booking/booking.component';
import {DialogComponent} from './dialog/dialog.component';
import {StatisticsComponent} from './statistics/statistics.component';
import {CalendarComponent} from './calendar/calendar.component';
import {CalendarModule} from "angular-calendar";


@NgModule({
    declarations: [

        AppComponent,
        NavigationBarComponent,
        FooterBarComponent,
        HomePageComponent,
        NavigationBarSubmenuComponent,
        ElementPageComponent,
        LoginPageComponent,
        RegisterPageComponent,
        AboutPageComponent,
        BuyPageComponent,
        ContactPageComponent,
        UserProfileComponent,
        DialogErrorComponent,
        RentPageComponent,
        AddPropertyComponent,
        EditPropertyComponent,
        DialogContentComponent,
        BookingComponent, DialogComponent, StatisticsComponent, CalendarComponent

    ],
    imports: [

        FormsModule,
        BrowserModule,
        AppRoutingModule,
        FontAwesomeModule,
        AngularFireStorageModule,
        NgxYoutubePlayerModule,
        CarouselModule,
        SlickCarouselModule,
        MatGridListModule,
        MatCardModule,
        MatPaginatorModule,
        HttpClientModule,
        ReactiveFormsModule,
        MatDialogModule,
        MatButtonModule,
        MatIconModule,
        CalendarModule,


    ],
    providers: [{provide: HTTP_INTERCEPTORS, useClass: TokenInterceptorService, multi: true}],
    bootstrap: [AppComponent]
})
export class AppModule {
}
