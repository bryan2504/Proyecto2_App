require 'test_helper'

class LeccionKatakanasControllerTest < ActionDispatch::IntegrationTest
  setup do
    @leccion_katakana = leccion_katakanas(:one)
  end

  test "should get index" do
    get leccion_katakanas_url
    assert_response :success
  end

  test "should get new" do
    get new_leccion_katakana_url
    assert_response :success
  end

  test "should create leccion_katakana" do
    assert_difference('LeccionKatakana.count') do
      post leccion_katakanas_url, params: { leccion_katakana: { katakana_id: @leccion_katakana.katakana_id, leccion: @leccion_katakana.leccion, significado: @leccion_katakana.significado, url_imagen: @leccion_katakana.url_imagen } }
    end

    assert_redirected_to leccion_katakana_url(LeccionKatakana.last)
  end

  test "should show leccion_katakana" do
    get leccion_katakana_url(@leccion_katakana)
    assert_response :success
  end

  test "should get edit" do
    get edit_leccion_katakana_url(@leccion_katakana)
    assert_response :success
  end

  test "should update leccion_katakana" do
    patch leccion_katakana_url(@leccion_katakana), params: { leccion_katakana: { katakana_id: @leccion_katakana.katakana_id, leccion: @leccion_katakana.leccion, significado: @leccion_katakana.significado, url_imagen: @leccion_katakana.url_imagen } }
    assert_redirected_to leccion_katakana_url(@leccion_katakana)
  end

  test "should destroy leccion_katakana" do
    assert_difference('LeccionKatakana.count', -1) do
      delete leccion_katakana_url(@leccion_katakana)
    end

    assert_redirected_to leccion_katakanas_url
  end
end
