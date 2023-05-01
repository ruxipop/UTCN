import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ElementPageComponent} from './element-page.component';

describe('ElementPageComponent', () => {
    let component: ElementPageComponent;
    let fixture: ComponentFixture<ElementPageComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [ElementPageComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(ElementPageComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
