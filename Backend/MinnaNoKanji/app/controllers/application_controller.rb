#$
class ApplicationController < ActionController::Base
  include DeviseTokenAuth::Concerns::SetUserByToken
  before_action :configure_permitted_parameters, if: :devise_controller?
  protect_from_forgery with: :null_session
  $flag_login = false
  #before_action :authenticate_administrator!
  #@@flag_sign_in = false


  protected

  def configure_permitted_parameters
    devise_parameter_sanitizer.permit(:sign_up, keys: [:email,:password,:name,:nickname,:image])
  end
end
