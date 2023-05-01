import {Component, Input, OnInit} from '@angular/core';
import {Appoinment} from "../models/appoinment";
import {PropertyService} from "../services/property.service";
import {MatDialog} from "@angular/material/dialog";
import {AuthService} from "../services/auth.service";

@Component({
    selector: 'app-calendar',
    templateUrl: './calendar.component.html',
    styleUrls: ['./calendar.component.scss']
})
export class CalendarComponent implements OnInit {

    @Input() currentProperty_id: number = 0;
    //@ts-ignore

    currentMonth: string;
    //@ts-ignore

    days: string[];
    //@ts-ignore

    dates: Date[];
    //@ts-ignore


    appointments: Appoinment[] = [];

    staticAppointments: Appoinment[] = [];
    //@ts-ignore
    selectedDate: Date | null;
    newAppointment: any;
    showStatistics: boolean = false;

    constructor(
        private authService: AuthService,
        private service: PropertyService, private dialog: MatDialog) {
    }

    ngOnInit() {
        console.log("prop", this.currentProperty_id)
        const now = new Date();
        this.currentMonth = now.toLocaleString('default', {month: 'long', year: 'numeric'});
        this.days = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
        this.dates = this.getDates(now.getFullYear(), now.getMonth());
        this.service.getAppsByProperty(this.currentProperty_id).subscribe((data) => {
                this.appointments = data
                this.staticAppointments = data
            }
        )
        //@ts-ignore

        this.selectedDate = null;
    }

    getDates(year: number, month: number): Date[] {
        const firstDay = new Date(year, month, 1);
        const lastDay = new Date(year, month + 1, 0);
        const daysInMonth = lastDay.getDate();
        const dates: Date[] = [];

        for (let i = 1; i <= daysInMonth; i++) {
            dates.push(new Date(year, month, i));
        }

        const daysBeforeMonth = firstDay.getDay();
        const daysAfterMonth = 6 - lastDay.getDay();

        for (let i = 0; i < daysBeforeMonth; i++) {
            //@ts-ignore

            dates.unshift(null);
        }

        for (let i = 0; i < daysAfterMonth; i++) {
            //@ts-ignore

            dates.push(null);
        }

        return dates;
    }

    prevMonth() {
        const currentYear = this.dates[15].getFullYear();
        const currentMonth = this.dates[15].getMonth();
        const prevMonth = currentMonth === 0 ? 11 : currentMonth - 1;
        const prevYear = prevMonth === 11 ? currentYear - 1 : currentYear;
        this.currentMonth = new Date(prevYear, prevMonth).toLocaleString('default', {month: 'long', year: 'numeric'});
        this.dates = this.getDates(prevYear, prevMonth);
        //@ts-ignore

        this.selectedDate = null;
    }

    nextMonth() {
        const currentYear = this.dates[15].getFullYear();
        const currentMonth = this.dates[15].getMonth();
        const nextMonth = currentMonth === 11 ? 0 : currentMonth + 1;
        const nextYear = nextMonth === 0 ? currentYear + 1 : currentYear;
        this.currentMonth = new Date(nextYear, nextMonth).toLocaleString('default', {month: 'long', year: 'numeric'});
        this.dates = this.getDates(nextYear, nextMonth);
        //@ts-ignore
        this.selectedDate = null;
    }

    selectDate(date: Date) {
        this.selectedDate = date;
    }


    getAppointmentsForSelectedDate(): Appoinment[] {


        this.appointments = this.staticAppointments.filter((appointment) => {
            const appointmentDate = new Date(appointment.start_date);
            const appointmentYear = appointmentDate.getFullYear();
            const appointmentMonth = appointmentDate.getMonth();
            const appointmentDay = appointmentDate.getDate();
            // Returneaza adevarat daca data si ora din programare sunt egale cu data si ora curenta
            // @ts-ignore

            return appointmentYear === this.selectedDate.getFullYear() &&
                // @ts-ignore
                appointmentMonth === this.selectedDate.getMonth() &&
                // @ts-ignore
                appointmentDay === this.selectedDate.getDate();
        })


        return this.appointments;
    }


    openDialogStatistics() {
        this.showStatistics = true;

    }


    transformInDate(data: Date) {
        let date = new Date(data)
        const hour = date.getHours();
        const minute = date.getMinutes();
        if (minute.toString().length == 1) {
            return hour + ':' + minute + '0'
        }
        return hour + ':' + minute
    }

}
