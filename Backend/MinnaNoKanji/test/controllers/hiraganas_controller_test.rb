require 'test_helper'

class HiraganasControllerTest < ActionDispatch::IntegrationTest
  setup do
    @hiragana = hiraganas(:one)
  end

  test "should get index" do
    get hiraganas_url
    assert_response :success
  end

  test "should get new" do
    get new_hiragana_url
    assert_response :success
  end

  test "should create hiragana" do
    assert_difference('Hiragana.count') do
      post hiraganas_url, params: { hiragana: { explicacion: @hiragana.explicacion, leccion: @hiragana.leccion } }
    end

    assert_redirected_to hiragana_url(Hiragana.last)
  end

  test "should show hiragana" do
    get hiragana_url(@hiragana)
    assert_response :success
  end

  test "should get edit" do
    get edit_hiragana_url(@hiragana)
    assert_response :success
  end

  test "should update hiragana" do
    patch hiragana_url(@hiragana), params: { hiragana: { explicacion: @hiragana.explicacion, leccion: @hiragana.leccion } }
    assert_redirected_to hiragana_url(@hiragana)
  end

  test "should destroy hiragana" do
    assert_difference('Hiragana.count', -1) do
      delete hiragana_url(@hiragana)
    end

    assert_redirected_to hiraganas_url
  end
end
