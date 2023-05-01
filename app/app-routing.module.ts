import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomePageComponent} from "./home-page/home-page.component";
import {ElementPageComponent} from "./element-page/element-page.component";
import {LoginPageComponent} from "./login-page/login-page.component";
import {RegisterPageComponent} from "./register-page/register-page.component";
import {AboutPageComponent} from "./about-page/about-page.component";
import {BuyPageComponent} from "./buy-page/buy-page.component";
import {ContactPageComponent} from "./contact-page/contact-page.component";
import {AuthGuard} from "./auth.guard";
import {UserProfileComponent} from "./user-profile/user-profile.component";
import {RentPageComponent} from "./rent-page/rent-page.component";
import {AddPropertyComponent} from "./add-property/add-property.component";
import {BookingComponent} from "./booking/booking.component";
import {StatisticsComponent} from "./statistics/statistics.component";
import {CalendarComponent} from "./calendar/calendar.component";

const routes: Routes = [
    {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
    },
    {
        path: 'login-page',
        component: LoginPageComponent
    },
    {
        path: 'home',
        component: HomePageComponent
    },
    {
        path: 'selected/:id',
        component: ElementPageComponent,
        canActivate: [AuthGuard],
        data: {
            allowedRoles: ['0', '1']
        }
    },

    {
        path: 'register-page',
        component: RegisterPageComponent
    },
    {
        path: 'about-page',
        component: AboutPageComponent
    },
    {
        path: 'buy-page',
        component: BuyPageComponent,
    },
    {
        path: 'contact-page',
        component: ContactPageComponent
    },
    {
        path: 'user-profile-page',
        component: UserProfileComponent,
        canActivate: [AuthGuard],
        data: {
            allowedRoles: ['0', '1']
        }
    },
    {
        path: 'rent-page',
        component: RentPageComponent
    },
    {
        path: 'add-page',
        component: AddPropertyComponent,
        canActivate: [AuthGuard],
        data: {
            allowedRoles: ['0']
        }
    },
    {
        path: 'app-book',
        component: BookingComponent,
        canActivate: [AuthGuard],
        data: {
            allowedRoles: ['1']
        }
    },
    {
        path: 'app-stat',
        component: StatisticsComponent,
        canActivate: [AuthGuard],
        data: {
            allowedRoles: ['0']
        }
    },
    {
        path: 'app-calendar',
        component: CalendarComponent, canActivate: [AuthGuard],
        data: {
            allowedRoles: ['0']
        }
    }
];


@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
