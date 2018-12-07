require 'test_helper'

class LeccionHiraganasControllerTest < ActionDispatch::IntegrationTest
  setup do
    @leccion_hiragana = leccion_hiraganas(:one)
  end

  test "should get index" do
    get leccion_hiraganas_url
    assert_response :success
  end

  test "should get new" do
    get new_leccion_hiragana_url
    assert_response :success
  end

  test "should create leccion_hiragana" do
    assert_difference('LeccionHiragana.count') do
      post leccion_hiraganas_url, params: { leccion_hiragana: { hiragana_id: @leccion_hiragana.hiragana_id, leccion: @leccion_hiragana.leccion, significado: @leccion_hiragana.significado, url_imagen: @leccion_hiragana.url_imagen } }
    end

    assert_redirected_to leccion_hiragana_url(LeccionHiragana.last)
  end

  test "should show leccion_hiragana" do
    get leccion_hiragana_url(@leccion_hiragana)
    assert_response :success
  end

  test "should get edit" do
    get edit_leccion_hiragana_url(@leccion_hiragana)
    assert_response :success
  end

  test "should update leccion_hiragana" do
    patch leccion_hiragana_url(@leccion_hiragana), params: { leccion_hiragana: { hiragana_id: @leccion_hiragana.hiragana_id, leccion: @leccion_hiragana.leccion, significado: @leccion_hiragana.significado, url_imagen: @leccion_hiragana.url_imagen } }
    assert_redirected_to leccion_hiragana_url(@leccion_hiragana)
  end

  test "should destroy leccion_hiragana" do
    assert_difference('LeccionHiragana.count', -1) do
      delete leccion_hiragana_url(@leccion_hiragana)
    end

    assert_redirected_to leccion_hiraganas_url
  end
end
