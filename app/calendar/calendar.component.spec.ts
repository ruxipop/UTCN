import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CalendarComponent} from './calendar.component';

describe('CalendarComponent', () => {
    let component: CalendarComponent;
    let fixture: ComponentFixture<CalendarComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [CalendarComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(CalendarComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
