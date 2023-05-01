import {Component, Input, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import "chart.js/auto";
import {Chart} from "chart.js";


@Component({
    selector: 'app-statistics',
    templateUrl: './statistics.component.html',
    styleUrls: ['./statistics.component.scss']
})
export class StatisticsComponent implements OnInit {

    appointmentsData: any = {};

    @Input() property_id: number = 0;
    chart: any

    constructor(private http: HttpClient) {
    }

    ngOnInit(): void {
        console.log(this.property_id)
        this.http.get('https://localhost:8000/appointments/property/' + this.property_id + '/stats/last-month/').subscribe(data => {
            this.appointmentsData = data;
            setTimeout(() => {
                this.generateChart();
            }, 0);
        });
    }

    generateChart(): void {

        if (this.chart) {
            this.chart.destroy();
        }


        this.chart = new Chart("myChart", {
            type: 'bar',
            data: {
                labels: this.appointmentsData.dates,
                datasets: [{
                    label: '# of Appointments',
                    data: this.appointmentsData.appointments,
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(255, 159, 64, 0.2)'
                    ],
                    borderColor: [
                        'rgba(255, 99, 132, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });

    }

    closeDialog() {

        const modalContainer = document.querySelector('.modal-container') as HTMLElement;
        const app = document.querySelector('.divChart') as HTMLElement;

        modalContainer.style.display = 'none';
        app.style.display = 'none';

    }

}
