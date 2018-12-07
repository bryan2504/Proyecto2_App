require 'test_helper'

class CuriosidadesJaponsControllerTest < ActionDispatch::IntegrationTest
  setup do
    @curiosidades_japon = curiosidades_japons(:one)
  end

  test "should get index" do
    get curiosidades_japons_url
    assert_response :success
  end

  test "should get new" do
    get new_curiosidades_japon_url
    assert_response :success
  end

  test "should create curiosidades_japon" do
    assert_difference('CuriosidadesJapon.count') do
      post curiosidades_japons_url, params: { curiosidades_japon: { explicacion: @curiosidades_japon.explicacion, tipo: @curiosidades_japon.tipo, url_imagen: @curiosidades_japon.url_imagen } }
    end

    assert_redirected_to curiosidades_japon_url(CuriosidadesJapon.last)
  end

  test "should show curiosidades_japon" do
    get curiosidades_japon_url(@curiosidades_japon)
    assert_response :success
  end

  test "should get edit" do
    get edit_curiosidades_japon_url(@curiosidades_japon)
    assert_response :success
  end

  test "should update curiosidades_japon" do
    patch curiosidades_japon_url(@curiosidades_japon), params: { curiosidades_japon: { explicacion: @curiosidades_japon.explicacion, tipo: @curiosidades_japon.tipo, url_imagen: @curiosidades_japon.url_imagen } }
    assert_redirected_to curiosidades_japon_url(@curiosidades_japon)
  end

  test "should destroy curiosidades_japon" do
    assert_difference('CuriosidadesJapon.count', -1) do
      delete curiosidades_japon_url(@curiosidades_japon)
    end

    assert_redirected_to curiosidades_japons_url
  end
end
