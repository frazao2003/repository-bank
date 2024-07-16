import { Routes } from '@angular/router';
import { SidebarComponent } from './component/sidebar/sidebar.component';
import { DepositoComponent } from './pages/deposito/deposito.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';
import { SaqueComponent } from './pages/saque/saque.component';
import { TransferenciaComponent } from './pages/transferencia/transferencia.component';

export const routes: Routes = [
    {path: 'login', component: LoginComponent},
    {path: 'sign', component: SignupComponent},
    {path: 'home', component: HomeComponent},
    {path: 'side', component: SidebarComponent},
    {path: 'deposito', component: DepositoComponent},
    {path: 'saque', component: SaqueComponent},
    {path: 'transferencia', component: TransferenciaComponent},

];
