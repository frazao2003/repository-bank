import { HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DefaultLoginLayoutComponent } from '../../component/default-login-layout/default-login-layout.component';
import { PrimaryInputComponent } from '../../component/primary-input/primary-input.component';
import { LoginRequestDTO } from '../../interface/LoginRequestDTO';
import { AuthService } from '../../service/auth.service';
import { UtilService } from '../../service/util.service';



interface LoginForm {
  cpf: FormControl<string | null>;
  password: FormControl<string | null>;
}


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    DefaultLoginLayoutComponent,
    ReactiveFormsModule,
    PrimaryInputComponent,
    HttpClientModule,
  ],
  providers: [
    AuthService,
    
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginForm!: FormGroup<LoginForm>;
  loginRequestDTO: LoginRequestDTO = { cpf: '', password: '' };

  constructor(
    private router: Router,
    private loginService: AuthService,
    private util: UtilService
    
  ){
    this.loginForm = new FormGroup({
      cpf: new FormControl('', [Validators.required, this.invalidCPF()]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)])
    })
  }
  invalidCPF(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const cpf = control.value; 
      if (!cpf) {
        return null;
      }
      return this.util.validaCPF(cpf) ? null : { invalidCPF: true };
    };
  }
  login(){
    this.loginRequestDTO.cpf = this.loginForm.value.cpf!;
    this.loginRequestDTO.password = this.loginForm.value.password!;
    this.loginService.login(this.loginRequestDTO).subscribe({
      next: (response) => {
        console.log('Login bem-sucedido:', response);
        this.router.navigate(["home"])
      },
      error: (err) => {
        console.error('Erro no login:', err);
      }
    });
    

  }

  navigate(){
    this.router.navigate(["sign"])
  }

}
