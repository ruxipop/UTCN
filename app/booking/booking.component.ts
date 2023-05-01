import {Component, Input} from '@angular/core';
import {Appoinment} from "../models/appoinment";
import {PropertyService} from "../services/property.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import * as moment from 'moment'


@Component({
    selector: 'app-booking',
    templateUrl: './booking.component.html',
    styleUrls: ['./booking.component.scss']
})
export class BookingComponent {
    @Input()
    property_id: number = 0;
    @Input()
    user_username: string = '';
    dates: Date[];
    times: string[] = ['9:00 AM', '9:30 AM', '10:00 AM', '10:30 AM', '11:00 AM', '11:30 AM', '12:00 PM', '12:30 PM', '1:00 PM', '1:30 PM', '2:00 PM', '2:30 PM', '3:00 PM', '3:30 PM', '4:00 PM', '4:30 PM', '5:00 PM'];
    selectedDate: Date = new Date();
    selectedTime: string = '';
    form = new FormGroup({
        time: new FormControl(null, Validators.required),
    })
    currentAppointments: Appoinment[] = [];

    constructor(private service: PropertyService) {
        this.service.getAppoinments().subscribe((data) => {
            //@ts-ignore
            this.currentAppointments = data.filter((app) => app.property_id === parseInt(this.property_id))
        });
        this.dates = this.getDates(new Date(), new Date(2030, 11, 31)); // Datele până la 31 decembrie 2030
        this.selectedTime = this.times[0];
    }


    getDates(startDate: Date, endDate: Date): Date[] {
        const dates = [];
        for (let date = startDate; date <= endDate; date.setDate(date.getDate() + 1)) {
            dates.push(new Date(date));
        }
        return dates;
    }


    selectDate(date: Date) {
        console.log("this ", this.property_id)
        this.service.getAppoinments().subscribe((data) => {
            //@ts-ignore
            this.currentAppointments = data.filter((app) => app.property_id === parseInt(this.property_id))
        });
        this.selectedDate = date;

    }


    checkAlreadyExistBooking(data: Date) {
        const filteredAppointments = this.currentAppointments.filter((appointment) => {
            // @ts-ignore
            const appointmentDate = new Date(appointment.start_date);
            const appointmentHour = appointmentDate.getHours();
            const appointmentMinutes = appointmentDate.getMinutes();
            const appointmentYear = appointmentDate.getFullYear();
            const appointmentMonth = appointmentDate.getMonth();
            const appointmentDay = appointmentDate.getDate();
            return appointmentHour === data.getHours() &&
                appointmentMinutes === data.getMinutes() &&
                appointmentYear === data.getFullYear() &&
                appointmentMonth === data.getMonth() &&
                appointmentDay === data.getDate();
        });
        return filteredAppointments.length != 0

    }
    
    convertStringToDate() {
        let time = this.selectedTime.match(/(\d+):(\d+)(\w+)/);
        // @ts-ignore
        console.log(time[2])
        // @ts-ignore
        let hours = parseInt(time[1]);
        // @ts-ignore
        let minutes = parseInt(time[2] + '0');
        // @ts-ignore
        
        let date = new Date();
        
        if (this.selectedTime.endsWith('PM') && hours !== 12) {
            console.log("Sal")
            hours += 12;
        } else if (this.selectedTime.endsWith('AM') && hours === 12) {
            hours = 0;
        }
        date.setHours(hours, minutes, 0, 0);
        return date;
    }

    onSubmit() {
        //@ts-ignore
        this.service.getAppoinments().subscribe((data) => {
            //@ts-ignore
            this.currentAppointments = data.filter((app) => app.property_id === parseInt(this.property_id))
            this.add()
        });
    }
    
    add() {
        let dataObj = this.convertStringToDate();
        this.selectedDate.setHours(dataObj.getHours());
        this.selectedDate.setMinutes(dataObj.getMinutes());
        this.selectedDate.setMilliseconds(0);
        this.selectedDate.setSeconds(0);

        let nextAvailableDate = new Date(this.selectedDate);

        if (this.checkAlreadyExistBooking(this.selectedDate)) {
            while (this.checkAlreadyExistBooking(nextAvailableDate)) {
                nextAvailableDate.setMinutes(nextAvailableDate.getMinutes() + 30);
            }
            alert(`Urmatoarea data disponibila este: ${nextAvailableDate}`);
        } else {
            this.service.createAppoiment(this.property_id, this.user_username, moment(this.selectedDate).format('YYYY-MM-DD HH:mm')).subscribe((date) => {
                this.currentAppointments.push(date)
            })
        }
    }


}
